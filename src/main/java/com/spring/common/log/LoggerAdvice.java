package com.spring.common.log;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.log4j.Log4j;

@Log4j
//���������� ��(bean)���� �ν��ϱ� ���ؼ� ���
@Component
//�ش� Ŭ������ ��ü�� Aspect�� ������ �������� ��Ÿ���� ���ؼ� ���
@Aspect
public class LoggerAdvice {

	//execution (@execution) �޼��带 �������� Pointcut�� ����
	//@Before = ��� ���
	//execution(*��θ� ����ش�*)
	// .. = ��� ������ �������ش�. ** = �յڿ� ������̴� �����´�.
	//������ �ش系���� ��� Impl.java�� �޼��� ����� ���´�.
	/*@Before("execution(* com.spring..*Impl.*(..))")
	public void printLogging() {
		log.info("-----------------------------------------------");
		log.info("[���� �α� log] ����Ͻ� ���� ���� �� ����");
		log.info("-----------------------------------------------");
	}*/
	
	//log���� ��ä���� �̰�ʹٸ�? args(bvo)�� �ְ� �Ű������� bvo�� �ش�.
	/*@Before("execution(* com.spring..*Impl.*(..)) && args(bvo)")
	public void printLogging(BoardVO bvo) {
		log.info("-----------------------------------------------");
		log.info("[���� �α� log] ����Ͻ� ���� ���� �� ����");
		log.info("-----------------------------------------------");
		log.info("BoardVO Ÿ���� bvo �Ķ���� �� : " + bvo);
	}*/
	
	//�Ű������� JoinPoint�� �ָ� �������� �Ű������� �迭�� ��ȯ�޴´�. �̷��� ��ȯ�޴� �迭�� ������ش�.
	/*@Before("execution(* com.spring..*Impl.*(..))")
	public void printLogging(JoinPoint jp) {
		log.info("-----------------------------------------------");
		log.info("[���� �α� log] ����Ͻ� ���� ���� �� ����");
		log.info("[ȣ�� �޼����]" + jp.getSignature().getName());
		log.info("[ȣ�� �޼����� �Ķ���� ��] "+Arrays.toString(jp.getArgs()));
		log.info("-----------------------------------------------");
	}*/
	
	//���ܰ� �߻��Ҷ� ����ϴ� ���
	// @AfterThrowing = ���ܹ߻��� ó������� ���´�.
	@AfterThrowing(pointcut="execution(* com.spring..*Impl.*(..))",throwing="exception")
	public void exceptionLogging(JoinPoint jp, Throwable exception) {
		log.info("-----------------------------------------------");
		log.info("[���ܹ߻�]");
		log.info("[���ܹ߻� �޼����] "+jp.getSignature().getName());
		//exception.printStackTrace();
		log.info("[���ܸ޼���] " +exception);
		log.info("------------------------------------------------");
	}
	
	//�����Ͻ� ���� �޼��尡 ���������� ���� �� �� ����
	/*@AfterReturning(pointcut="execution(* com.spring..*Impl.*(..))")
	public void afterReturningMethod(JoinPoint jp) {
		log.info("-------------------------------------------------");
		log.info("[���� �α� log] �����Ͻ� ���� ���� �� ���");
		log.info("afterReturningMethod() called..........."+jp.getSignature().getName());
		log.info("--------------------------------------------------");
	} */
	
	@Around("execution(* com.spring..*Impl.*(..))")
	public Object timeLoggin(ProceedingJoinPoint pjp) throws Throwable{
		log.info("-------------------------------------------------");
		log.info("[���� �α� log] ����Ͻ� ���� ���� �� ����");
		//long StartpTime = system.currentTimeMillis();
		StopWatch watch = new StopWatch();
		watch.start();
		log.info(Arrays.toString(pjp.getArgs()));
		
		Object result = null;
		
		// proceed() : ���� target ��ü�� �޼��带 �����ϴ� ���. ���⼭ ����Ͻ� ���� ����
		result = pjp.proceed();
		
		//long endTime = system.currentTimeMillis();
		watch.stop();
		
		log.info("Class: "+pjp.getTarget().getClass());
		//logger.info(pjp.getTarget().getClass()+)
		log.info(pjp.getSignature().getName()+" : �ҿ�ð� "+watch.getTotalTimeSeconds()+"ms");
		log.info("[���� �α� log] ����Ͻ� ���� ���� �� ����");
		log.info("----------------------------------------------------");
		return result;
	}
}
