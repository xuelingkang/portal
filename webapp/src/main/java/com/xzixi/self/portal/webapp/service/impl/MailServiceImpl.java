package com.xzixi.self.portal.webapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xzixi.self.portal.framework.service.impl.BaseServiceImpl;
import com.xzixi.self.portal.webapp.data.IMailData;
import com.xzixi.self.portal.webapp.model.po.Attachment;
import com.xzixi.self.portal.webapp.model.po.Mail;
import com.xzixi.self.portal.webapp.model.po.MailContent;
import com.xzixi.self.portal.webapp.model.po.User;
import com.xzixi.self.portal.webapp.model.vo.MailVO;
import com.xzixi.self.portal.webapp.service.IAttachmentService;
import com.xzixi.self.portal.webapp.service.IMailContentService;
import com.xzixi.self.portal.webapp.service.IMailService;
import com.xzixi.self.portal.webapp.service.IUserService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@Service
public class MailServiceImpl extends BaseServiceImpl<IMailData, Mail> implements IMailService {

    @Autowired
    private IMailContentService mailContentService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAttachmentService attachmentService;

    @Override
    public MailVO buildVO(Mail mail, MailVO.BuildOption option) {
        MailVO mailVO = new MailVO(mail);
        if (option.isSendUser() && mail.getSendUserId() != null) {
            User sendUser = userService.getById(mail.getSendUserId(), false);
            mailVO.setSendUser(sendUser);
        }
        if (option.isToUsers() && CollectionUtils.isNotEmpty(mail.getToUserIds())) {
            Collection<User> toUsers = userService.listByIds(mail.getToUserIds());
            mailVO.setToUsers(toUsers);
        }
        if (option.isAttachments() && CollectionUtils.isNotEmpty(mail.getAttachmentIds())) {
            Collection<Attachment> attachments = attachmentService.listByIds(mail.getAttachmentIds());
            mailVO.setAttachments(attachments);
        }
        if (option.isContent()) {
            MailContent content = mailContentService.getOne(new QueryWrapper<>(new MailContent().setMailId(mail.getId())));
            mailVO.setContent(content);
        }
        return mailVO;
    }

    @Override
    public List<MailVO> buildVO(Collection<Mail> mails, MailVO.BuildOption option) {
        List<MailVO> mailVOList = mails.stream().map(MailVO::new).collect(Collectors.toList());
        if (option.isSendUser()) {
            List<Integer> sendUserIds = mails.stream().filter(mail -> mail.getSendUserId() != null)
                    .map(Mail::getSendUserId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(sendUserIds)) {
                Collection<User> sendUsers = userService.listByIds(sendUserIds);
                mailVOList.forEach(mailVO -> {
                    User sendUser = sendUsers.stream().filter(user -> user.getId().equals(mailVO.getSendUserId())).findFirst().orElse(null);
                    mailVO.setSendUser(sendUser);
                });
            }
        }
        if (option.isToUsers()) {
            List<Integer> allToUserIds = new ArrayList<>();
            mails.forEach(mail -> allToUserIds.addAll(mail.getToUserIds()));
            if (CollectionUtils.isNotEmpty(allToUserIds)) {
                Collection<User> allToUsers = userService.listByIds(allToUserIds);
                mailVOList.forEach(mailVO -> {
                    List<User> toUsers = allToUsers.stream()
                            .filter(user -> CollectionUtils.isNotEmpty(mailVO.getToUserIds()) && mailVO.getToUserIds().contains(user.getId()))
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(toUsers)) {
                        mailVO.setToUsers(toUsers);
                    }
                });
            }
        }
        if (option.isAttachments()) {
            List<Integer> allAttachmentIds = new ArrayList<>();
            mails.forEach(mail -> allAttachmentIds.addAll(mail.getAttachmentIds()));
            if (CollectionUtils.isNotEmpty(allAttachmentIds)) {
                Collection<Attachment> allAttachments = attachmentService.listByIds(allAttachmentIds);
                mailVOList.forEach(mailVO -> {
                    List<Attachment> attachments = allAttachments.stream()
                            .filter(attachment -> CollectionUtils.isNotEmpty(mailVO.getAttachmentIds()) && mailVO.getAttachmentIds().contains(attachment.getId()))
                            .collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(attachments)) {
                        mailVO.setAttachments(attachments);
                    }
                });
            }
        }
        if (option.isContent()) {
            List<Integer> mailIds = mails.stream().map(Mail::getId).collect(Collectors.toList());
            List<MailContent> contents = mailContentService.listByMailIds(mailIds);
            if (CollectionUtils.isNotEmpty(contents)) {
                mailVOList.forEach(mailVO -> contents.stream()
                        .filter(mailContent -> mailContent.getMailId().equals(mailVO.getId()))
                        .findFirst().ifPresent(mailVO::setContent));
            }
        }
        return mailVOList;
    }
}
