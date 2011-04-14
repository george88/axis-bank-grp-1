package de.axis_bank;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class BankService {

	public final static int Liqui_1 = 1;
	public final static int Liqui_2 = 2;
	public final static int Liqui_3 = 3;
	public final static int Liqui_4 = 4;
	public final static int Liqui_5 = 5;
	private final int logoffTimeInSeconds = 720;
	private int currentTime;
	private boolean login;
	private Timer timer;

	public BankService() {

	}

	public boolean login(String username, String password) {
		// if(UserDAO.userExists()){
		// if(UserDAO.passwordCorrect()){
		setLogin(true);
		continueLogoffCounter();
		// }
		// }

		return isLogin();
	}

	public void logoff() {
		login = false;
	}

	private void continueLogoffCounter() {
		if (isLogin()) {
			if (timer != null) {
				currentTime = logoffTimeInSeconds;
				timer = new Timer(1000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						currentTime--;
						if (currentTime < 1) {
							logoff();
						}
					}
				});
			}
			currentTime = logoffTimeInSeconds;
		}
	}

	public int getLiquidity() {
		if (!isLogin())
			return -1;
		continueLogoffCounter();

		return Liqui_1;
	}

	public RepaymentPlan getRepaymentPlan() {
		if (!isLogin())
			return null;
		continueLogoffCounter();

		return new RepaymentPlan();
	}

	private void setLogin(boolean login) {
		this.login = login;
	}

	public boolean isLogin() {
		return login;
	}

}
