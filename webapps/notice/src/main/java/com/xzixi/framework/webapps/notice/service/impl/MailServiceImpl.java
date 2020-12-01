/*
 * The spring-based xzixi framework simplifies development.
 *
 * Copyright (C) 2020  xuelingkang@163.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.xzixi.framework.webapps.notice.service.impl;

import com.xzixi.framework.boot.core.exception.ServerException;
import com.xzixi.framework.boot.persistent.service.impl.BaseServiceImpl;
import com.xzixi.framework.webapps.common.model.enums.MailStatus;
import com.xzixi.framework.webapps.common.model.po.Mail;
import com.xzixi.framework.webapps.common.model.po.MailContent;
import com.xzixi.framework.webapps.common.model.vo.MailVO;
import com.xzixi.framework.webapps.notice.constant.MailConstant;
import com.xzixi.framework.webapps.notice.data.IMailData;
import com.xzixi.framework.webapps.notice.service.IMailContentService;
import com.xzixi.framework.webapps.notice.service.IMailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 薛凌康
 */
@Slf4j
@Service
public class MailServiceImpl extends BaseServiceImpl<IMailData, Mail> implements IMailService {

    @Value("${spring.mail.username:}")
    private String mailUsername;
    @Autowired
    private IMailContentService mailContentService;
//    @Autowired
//    private IUserService userService;
//    @Autowired
//    private IAttachmentService attachmentService;
    @Autowired
    private JavaMailSender javaMailSender;
//    @Autowired
//    private ISftpClient sftpClient;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMail(Mail mail, MailContent content) {
        mail.setStatus(MailStatus.UNSENT);
        if (mail.getSendUserId() == null) {
            mail.setSendUserId(MailConstant.SEND_MAIL_USER_ID);
        }
        if (!save(mail)) {
            throw new ServerException(mail, "保存邮件失败！");
        }
        content.setMailId(mail.getId());
        if (!mailContentService.save(content)) {
            throw new ServerException(content, "保存邮件内容失败！");
        }
    }

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void send(Mail mail, MailContent content) {
//        try {
//            String[] toMails;
//            if (CollectionUtils.isNotEmpty(mail.getToUserIds())) {
//                Collection<User> toUsers = userService.listByIds(mail.getToUserIds());
//                // 收件地址
//                toMails = toUsers.stream().map(User::getEmail).toArray(String[]::new);
//            } else {
//                toMails = new String[]{mail.getToEmail()};
//            }
//            // 邮件附件
//            Map<String, ByteArrayResource> resources = new HashMap<>();
//            if (CollectionUtils.isNotEmpty(mail.getAttachmentIds())) {
//                Collection<Attachment> attachments = attachmentService.listByIds(mail.getAttachmentIds());
//                if (CollectionUtils.isNotEmpty(attachments)) {
//                    sftpClient.open(sftp -> attachments.forEach(attachment -> {
//                        String address = attachment.getAddress();
//                        String dir = address.substring(0, address.lastIndexOf(AttachmentConstant.SEPARATOR));
//                        String name = address.substring(address.lastIndexOf(AttachmentConstant.SEPARATOR) + 1);
//                        ByteArrayResource resource = new ByteArrayResource(sftp.download(dir, name));
//                        resources.put(attachment.getName(), resource);
//                    }));
//                }
//            }
//            // 发送邮件
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
//            messageHelper.setFrom(mailUsername);
//            messageHelper.setTo(toMails);
//            messageHelper.setSubject(mail.getSubject());
//            messageHelper.setText(content.getContent(), true);
//            if (resources.size() > 0) {
//                for (Map.Entry<String, ByteArrayResource> entry: resources.entrySet()) {
//                    messageHelper.addAttachment(MimeUtility.decodeText(entry.getKey()), entry.getValue());
//                }
//            }
//            javaMailSender.send(message);
//            mail.setStatus(MailStatus.SUCCESS);
//            updateById(mail);
//        } catch (Exception e) {
//            // 这里不能抛异常，抛异常会回滚事务，无法修改邮件的状态
//            mail.setStatus(MailStatus.FAILURE);
//            updateById(mail);
//            log.error("发送邮件失败！", e);
//        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeMailsByIds(Collection<Integer> ids) {
//        Collection<Mail> mails = listByIds(ids);
//        if (!removeByIds(ids)) {
//            throw new ServerException(ids, "删除邮件失败！");
//        }
//        List<Integer> attachmentIds = new ArrayList<>();
//        mails.forEach(mail -> {
//            Collection<Integer> attachmentIdList = mail.getAttachmentIds();
//            if (CollectionUtils.isNotEmpty(attachmentIdList)) {
//                attachmentIds.addAll(attachmentIdList);
//            }
//        });
//        if (CollectionUtils.isNotEmpty(attachmentIds)) {
//            attachmentService.removeAttachmentsByIds(attachmentIds);
//        }
//        List<MailContent> contents = mailContentService.listByMailIds(ids);
//        if (CollectionUtils.isNotEmpty(contents)) {
//            List<Integer> contentIds = contents.stream().map(MailContent::getId).collect(Collectors.toList());
//            if (!mailContentService.removeByIds(contentIds)) {
//                throw new ServerException(contentIds, "删除邮件内容失败！");
//            }
//        }
    }

    @Override
    public MailVO buildVO(Mail mail, MailVO.BuildOption option) {
        MailVO mailVO = new MailVO(mail);
//        if (option.isSendUser() && mail.getSendUserId() != null) {
//            User sendUser = userService.getById(mail.getSendUserId(), false);
//            if (sendUser != null) {
//                sendUser.setPassword(null);
//            }
//            mailVO.setSendUser(sendUser);
//        }
//        if (option.isToUsers() && CollectionUtils.isNotEmpty(mail.getToUserIds())) {
//            Collection<User> toUsers = userService.listByIds(mail.getToUserIds());
//            if (CollectionUtils.isNotEmpty(toUsers)) {
//                toUsers.forEach(user -> user.setPassword(null));
//                mailVO.setToUsers(toUsers);
//            }
//        }
//        if (option.isAttachments() && CollectionUtils.isNotEmpty(mail.getAttachmentIds())) {
//            Collection<Attachment> attachments = attachmentService.listByIds(mail.getAttachmentIds());
//            mailVO.setAttachments(attachments);
//        }
//        if (option.isContent()) {
//            MailContent content = mailContentService.getOne(new QueryParams<>(new MailContent().setMailId(mail.getId())));
//            mailVO.setContent(content);
//        }
        return mailVO;
    }

    @Override
    public List<MailVO> buildVO(Collection<Mail> mails, MailVO.BuildOption option) {
        List<MailVO> mailVOList = mails.stream().map(MailVO::new).collect(Collectors.toList());
//        if (option.isSendUser()) {
//            List<Integer> sendUserIds = mails.stream().filter(mail -> mail.getSendUserId() != null)
//                    .map(Mail::getSendUserId).collect(Collectors.toList());
//            if (CollectionUtils.isNotEmpty(sendUserIds)) {
//                Collection<User> sendUsers = userService.listByIds(sendUserIds);
//                mailVOList.forEach(mailVO -> {
//                    User sendUser = sendUsers.stream().filter(user -> Objects.equals(user.getId(), mailVO.getSendUserId())).findFirst().orElse(null);
//                    if (sendUser != null) {
//                        sendUser.setPassword(null);
//                    }
//                    mailVO.setSendUser(sendUser);
//                });
//            }
//        }
//        if (option.isToUsers()) {
//            List<Integer> allToUserIds = new ArrayList<>();
//            mails.forEach(mail -> allToUserIds.addAll(mail.getToUserIds()));
//            if (CollectionUtils.isNotEmpty(allToUserIds)) {
//                Collection<User> allToUsers = userService.listByIds(allToUserIds);
//                mailVOList.forEach(mailVO -> {
//                    List<User> toUsers = allToUsers.stream()
//                            .filter(user -> CollectionUtils.isNotEmpty(mailVO.getToUserIds()) && mailVO.getToUserIds().contains(user.getId()))
//                            .collect(Collectors.toList());
//                    if (CollectionUtils.isNotEmpty(toUsers)) {
//                        toUsers.forEach(user -> user.setPassword(null));
//                        mailVO.setToUsers(toUsers);
//                    }
//                });
//            }
//        }
//        if (option.isAttachments()) {
//            List<Integer> allAttachmentIds = new ArrayList<>();
//            mails.forEach(mail -> allAttachmentIds.addAll(mail.getAttachmentIds()));
//            if (CollectionUtils.isNotEmpty(allAttachmentIds)) {
//                Collection<Attachment> allAttachments = attachmentService.listByIds(allAttachmentIds);
//                mailVOList.forEach(mailVO -> {
//                    List<Attachment> attachments = allAttachments.stream()
//                            .filter(attachment -> CollectionUtils.isNotEmpty(mailVO.getAttachmentIds()) && mailVO.getAttachmentIds().contains(attachment.getId()))
//                            .collect(Collectors.toList());
//                    if (CollectionUtils.isNotEmpty(attachments)) {
//                        mailVO.setAttachments(attachments);
//                    }
//                });
//            }
//        }
//        if (option.isContent()) {
//            List<Integer> mailIds = mails.stream().map(Mail::getId).collect(Collectors.toList());
//            List<MailContent> contents = mailContentService.listByMailIds(mailIds);
//            if (CollectionUtils.isNotEmpty(contents)) {
//                mailVOList.forEach(mailVO -> contents.stream()
//                        .filter(mailContent -> Objects.equals(mailVO.getId(), mailContent.getMailId()))
//                        .findFirst().ifPresent(mailVO::setContent));
//            }
//        }
        return mailVOList;
    }
}
