package frc.lib.Util;

public class CasseroleCrashHandler implements Thread.UncaughtExceptionHandler {
    @Override
	public void uncaughtException(Thread thread, Throwable t) {
		CrashTracker.logThrowableCrash(t);
		thread.getDefaultUncaughtExceptionHandler().uncaughtException(thread, t);
	}
}