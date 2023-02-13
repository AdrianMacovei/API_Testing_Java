package io.dummy_api.log;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


@Slf4j
public class PlainTextReporter implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        log.info("|*|*|*|*|*|*| STARTING TEST {}:{} |*|*|*|*|*|*|", result.getMethod().getRealClass().getSimpleName(),
                result.getMethod().getConstructorOrMethod().getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log.info("|*|*|*|*|*|*| Ending test {}:{} {} |*|*|*|*|*|*|*|*", result.getMethod().getRealClass().getSimpleName(),
                result.getMethod().getConstructorOrMethod().getName(), "SUCCESS");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log.info("|*|*|*|*|*|*| Ending test {}:{} {} |*|*|*|*|*|*|*|*", result.getMethod().getRealClass().getSimpleName(),
                result.getMethod().getConstructorOrMethod().getName(), "!!! FAILURE !!!");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log.info(result.getMethod().getConstructorOrMethod().getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log.info(result.getMethod().getConstructorOrMethod().getName());
    }

    @Override
    public void onStart(ITestContext context) {
        log.info(context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        log.info(context.getName());
    }
}