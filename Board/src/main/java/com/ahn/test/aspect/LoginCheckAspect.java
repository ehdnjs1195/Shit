package com.ahn.test.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


//@Aspect
//@Component
public class LoginCheckAspect {
//	@Pointcut("execution(* com.ahn.test.UsersController.get*(..)) || execution(* kr.coo.civ.MemberController.set*(..))")
//	public void usersPoint() {
//	}

//	@Pointcut("execution(* kr.coo.civ.MovieController.commentWrite(..))")
//	public void movieDetailPoint() {
//	}
//
//	@Pointcut("execution(* kr.coo.civ.MoviePaymentController.get*(..)) || execution(* kr.coo.civ.MoviePaymentController.set*(..))")
//	public void paymentPoint() {
//	}

//	@Around("usersPoint()")
	public Object sessionCheck(ProceedingJoinPoint pjp) throws Throwable {
		HttpSession session = null;
		HttpServletRequest request = null;
		Object result = null;
		String type = pjp.getSignature().getDeclaringTypeName();
		for (Object o : pjp.getArgs()) {
			if (o instanceof HttpSession) {
				session = (HttpSession) o;
			} else if (o instanceof HttpServletRequest) {
				request = (HttpServletRequest) o;
			}
		}
		if (session.getAttribute("id") == null) {
			session.setAttribute("redirectUrl", request.getServletPath());
			return "redirect:/users/loginform.do";
		} else {
			if (session.getAttribute("redirectUrl") != null) {
				session.removeAttribute("redirectUrl");
			}
			result = pjp.proceed();
		}

		return result;
	}

}
