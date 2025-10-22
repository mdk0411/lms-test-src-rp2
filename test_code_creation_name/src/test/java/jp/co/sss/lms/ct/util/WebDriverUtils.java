package jp.co.sss.lms.ct.util;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

/**
 * Webドライバーユーティリティ
 * @author holy
 */
public class WebDriverUtils {

	/** Webドライバ */
	public static WebDriver webDriver;

	/**
	 * インスタンス取得
	 * @return Webドライバ
	 */
	public static void createDriver() {
		System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
		webDriver = new ChromeDriver();
	}

	/**
	 * インスタンス終了
	 */
	public static void closeDriver() {
		webDriver.quit();
	}

	/**
	 * 画面遷移
	 * @param url
	 */
	public static void goTo(String url) {
		webDriver.get(url);
		pageLoadTimeout(5);
	}

	/**
	 * ページロードタイムアウト設定
	 * @param second
	 */
	public static void pageLoadTimeout(int second) {
		webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(second));
	}

	/**
	 * 要素の可視性タイムアウト設定
	 * @param locater
	 * @param second
	 */
	public static void visibilityTimeout(By locater, int second) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(second));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locater));
	}

	/**
	 * 指定ピクセル分だけスクロール
	 * @param pixel
	 */
	public static void scrollBy(String pixel) {
		((JavascriptExecutor) webDriver).executeScript("window.scrollBy(0," + pixel + ");");
	}

	/**
	 * 指定位置までスクロール
	 * @param pixel
	 */
	public static void scrollTo(String pixel) {
		((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0," + pixel + ");");
	}

	/**
	 * エビデンス取得
	 * @param instance
	 */
	public static void getEvidence(Object instance) {
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			String className = instance.getClass().getEnclosingClass().getSimpleName();
			String methodName = instance.getClass().getEnclosingMethod().getName();
			Files.move(tempFile, new File("evidence\\" + className + "_" + methodName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * エビデンス取得（サフィックスあり）
	 * @param instance
	 * @param suffix
	 */
	// ●改修：2025/10/22  河島
	//       evidenceファルダ内にクラスごとのサブフォルダを自動生成するよう修正
	public static void getEvidence(Object instance, String suffix) {
		File tempFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			String className = instance.getClass().getEnclosingClass().getSimpleName();
			String methodName = instance.getClass().getEnclosingMethod().getName();

			// ●追加開始：サブフォルダ作成
			File dir = new File(System.getProperty("user.dir"), "evidence/" + className);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			// ●修正：保存先をクラス名フォルダ配下に変更
			File destFile = new File(dir, className + "_" + methodName + "_" + suffix + ".png");
			Files.copy(tempFile, destFile);

			// ●追加：保存確認ログ出力
			System.out.println("◎スクリーンショット保存完了: " + destFile.getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
			// ●例外追加
		} catch (Exception e) {
			System.err.println("×スクリーンショット保存失敗: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 指定したIDの要素が存在するか確認
	 * 
	 * @param id 要素ID
	 * @return 要素が存在すればtrue、存在しなければfalse
	 * @author 河島
	 */
	public static boolean isElementPresentById(String id) {
		try {
			webDriver.findElement(By.id(id));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public static boolean isElementPresentByCssSelector(String selector) {
		try {
			webDriver.findElement(By.cssSelector(selector));
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

}
