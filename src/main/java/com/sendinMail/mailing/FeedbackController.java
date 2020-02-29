package com.sendinMail.mailing;

import javax.validation.ValidationException;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("unused")
@RestController
//@RequestMapping("/feedback")
@CrossOrigin
public class FeedbackController {

	private MailConfig mcfg;
	
	
	public FeedbackController(MailConfig mcfg) {
		this.mcfg = mcfg;
	}
	
	@PostMapping(value="/feedback")
	public void sendFeedback(@RequestBody Feedback feedback, BindingResult bindingResult) {
		
			if(bindingResult.hasErrors()) {
				throw new ValidationException("feedback is not valid");
			}
			
			
			// CRETE A MAIL SENDER
			JavaMailSenderImpl mailsender = new JavaMailSenderImpl();
			mailsender.setHost(this.mcfg.getHost());
			mailsender.setPort(this.mcfg.getPort());
			mailsender.setUsername(this.mcfg.getUsername());
			mailsender.setPassword(this.mcfg.getPassword());
			
			// CREATE A MAIL 
			SimpleMailMessage simpleMail = new SimpleMailMessage();
			simpleMail.setFrom(feedback.getEmail());
			simpleMail.setTo("aaa@gmail.com");
			simpleMail.setSubject("new feedback from "+feedback.getName());
			simpleMail.setText(feedback.getFeedback());
			
			//send mail
			mailsender.send(simpleMail);
			
	}
}
