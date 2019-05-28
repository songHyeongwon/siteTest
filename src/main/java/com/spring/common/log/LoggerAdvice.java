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
//스프링에서 빈(bean)으로 인식하기 위해서 사용
@Component
//해당 클래스의 객체가 Aspect를 구현한 것임으로 나타내기 위해서 사용
@Aspect
public class LoggerAdvice {

	//execution (@execution) 메서드를 기준으로 Pointcut을 설정
	//@Before = 경로 잡기
	//execution(*경로를 잡아준다*)
	// .. = 모든 내용을 가져와준다. ** = 앞뒤에 어떤내용이던 가져온다.
	//요컨데 해당내용은 모든 Impl.java의 메서드 실행시 나온다.
	/*@Before("execution(* com.spring..*Impl.*(..))")
	public void printLogging() {
		log.info("-----------------------------------------------");
		log.info("[공통 로그 log] 비즈니스 로직 수행 전 동작");
		log.info("-----------------------------------------------");
	}*/
	
	//log에서 개채값을 뽑고싶다면? args(bvo)를 주고 매개변수에 bvo를 준다.
	/*@Before("execution(* com.spring..*Impl.*(..)) && args(bvo)")
	public void printLogging(BoardVO bvo) {
		log.info("-----------------------------------------------");
		log.info("[공통 로그 log] 비즈니스 로직 수행 전 동작");
		log.info("-----------------------------------------------");
		log.info("BoardVO 타입의 bvo 파라미터 값 : " + bvo);
	}*/
	
	//매개변수로 JoinPoint를 주면 가져오는 매개변수를 배열로 반환받는다. 이렇게 반환받는 배열을 출력해준다.
	/*@Before("execution(* com.spring..*Impl.*(..))")
	public void printLogging(JoinPoint jp) {
		log.info("-----------------------------------------------");
		log.info("[공통 로그 log] 비즈니스 로직 수행 전 동작");
		log.info("[호출 메서드명]" + jp.getSignature().getName());
		log.info("[호출 메서드의 파라미터 값] "+Arrays.toString(jp.getArgs()));
		log.info("-----------------------------------------------");
	}*/
	
	//예외가 발생할때 출력하는 방법
	// @AfterThrowing = 예외발생후 처리방법을 적는다.
	@AfterThrowing(pointcut="execution(* com.spring..*Impl.*(..))",throwing="exception")
	public void exceptionLogging(JoinPoint jp, Throwable exception) {
		log.info("-----------------------------------------------");
		log.info("[예외발생]");
		log.info("[예외발생 메서드명] "+jp.getSignature().getName());
		//exception.printStackTrace();
		log.info("[예외메세지] " +exception);
		log.info("------------------------------------------------");
	}
	
	//비지니스 로직 메서드가 정상적으로 수행 된 후 동작
	/*@AfterReturning(pointcut="execution(* com.spring..*Impl.*(..))")
	public void afterReturningMethod(JoinPoint jp) {
		log.info("-------------------------------------------------");
		log.info("[공통 로그 log] 비지니스 로직 수행 후 출력");
		log.info("afterReturningMethod() called..........."+jp.getSignature().getName());
		log.info("--------------------------------------------------");
	} */
	
	@Around("execution(* com.spring..*Impl.*(..))")
	public Object timeLoggin(ProceedingJoinPoint pjp) throws Throwable{
		log.info("-------------------------------------------------");
		log.info("[공통 로그 log] 비즈니스 로직 수행 전 동작");
		//long StartpTime = system.currentTimeMillis();
		StopWatch watch = new StopWatch();
		watch.start();
		log.info(Arrays.toString(pjp.getArgs()));
		
		Object result = null;
		
		// proceed() : 실제 target 객체의 메서드를 실행하는 기능. 여기서 비즈니스 로직 실행
		result = pjp.proceed();
		
		//long endTime = system.currentTimeMillis();
		watch.stop();
		
		log.info("Class: "+pjp.getTarget().getClass());
		//logger.info(pjp.getTarget().getClass()+)
		log.info(pjp.getSignature().getName()+" : 소요시간 "+watch.getTotalTimeSeconds()+"ms");
		log.info("[공통 로그 log] 비즈니스 로직 수행 후 동작");
		log.info("----------------------------------------------------");
		return result;
	}
}
