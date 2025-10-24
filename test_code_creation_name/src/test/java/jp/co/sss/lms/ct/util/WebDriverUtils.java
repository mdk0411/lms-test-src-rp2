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
import org.openqa.selenium.WebElement;
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
			System.out.println("◎エビデンス取得完了: " + destFile.getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
			// ●例外追加
		} catch (Exception e) {
			System.err.println("×エビデンス取得失敗: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// 以下、①～⑧は河島が追加した機能
	/**
	 * ①10/22 指定したIDの要素が存在するか確認
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

	/**
	 * ②10/23 現在のURLが指定したパスと一致するか確認
	 * 
	 * @param expectedPath 期待される末尾（部分一致でOK）
	 * @return 一致していればtrue、しなければfalse
	 * @author 河島
	 */
	public static boolean isUrlEndsWith(String expectedPath) {
		String currentUrl = webDriver.getCurrentUrl();
		System.out.println("◎現在のURL : " + currentUrl);
		return currentUrl.endsWith(expectedPath);
	}

	/**
	 * ③10/23 現在のページタイトルが指定した文字列と完全一致するか確認
	 * 
	 * @param expectedTitle 期待するタイトル
	 * @return 一致していればtrue、しなければfalse
	 * @author 河島
	 */
	public static boolean isTitle(String expectedTitle) {
		String actualTitle = webDriver.getTitle();
		System.out.println("◎現在のタイトル : " + actualTitle);
		return actualTitle.equals(expectedTitle);
	}

	/**
	 * ④10/23 別タブに切り替える
	 * 
	 * @return 別タブのウィンドウハンドル<p>
	 *          新しい食べが見つからなかった場合、現在のウィンドウハンドルを返す
	 * </p>
	 * @author 河島
	 */
	public static String switchToNewWindow() {
		String currentWindow = webDriver.getWindowHandle();
		for (String handle : webDriver.getWindowHandles()) {
			if (!handle.equals(currentWindow)) {
				webDriver.switchTo().window(handle);
				System.out.println("◎新しいタブに切り替えました。");
				return handle;
			}
		}
		System.out.println("△新しいタブが見つからなかったため、現在のウィンドウを継続します。");
		return currentWindow;
	}

	/**
	 * ⑤10/23 別タブを閉じて元のウィンドウに戻る
	 * 
	 * @param originalWindow　元のウィンドウハンドル
	 * @author 河島
	 */
	public static void closeAndReturnWindow(String originalWindow) {
		try {
			// 現在のウィンドウが存在するか確認
			if (webDriver.getWindowHandles().size() > 1) {
				webDriver.close();
				webDriver.switchTo().window(originalWindow);
				System.out.println("◎別タブを閉じ、元のウィンドウに戻りました。");
			} else {
				System.out.println("△別タブが存在しないため、close処理はスキップしました。");
			}
		} catch (Exception e) {
			System.err.println("×ウィンドウ切り替え中にエラー: " + e.getMessage());
		}
	}

	/**
	 * ⑥10/24 画面上部のメニュー（「機能」など）を開く
	 *
	 * @param menuLabel 開きたいメニューのラベルのテキスト
	 * @author 河島
	 */
	public static void openDropDown(String menuLabel) {

		// 「機能」などのメニュー項目をクリックするまで10秒待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

		// 開きたいメニューのラベルを探す
		WebElement menuButton = wait.until(
				ExpectedConditions.elementToBeClickable(
						By.xpath("//a[contains(text(),'" + menuLabel + "')]")));

		// メニューを押下
		menuButton.click();

		// メニューが開いたことを確認
		wait.until(
				ExpectedConditions.presenceOfElementLocated(
						By.cssSelector("ul.dropdown-menu")));

	}

	/**
	 * ⑦10/24 ドロップダウン内のリンクを押下する処理（/common/headerのファイル共有、動画視聴、サポートセンター、ヘルプに対応）
	 * @param menuLabel ヘッダーのラベル
	 * @param linkText ドロップダウン内のリンク
	 * @author 河島
	 */
	public static void clickDropDownLink(String menuLabel, String linkText) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));

		// メニュー項目を探す（例：「機能」）
		WebElement menuButton = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//a[contains(@class,'dropdown-toggle') and contains(text(),'" + menuLabel + "')]")));

		// JavaScriptで確実にクリック
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", menuButton);

		// メニューが開いて「リンクが見える」まで待つ（visibilityOfElementLocated）
		WebElement link = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//ul[contains(@class,'dropdown-menu')]//a[contains(normalize-space(text()),'" + linkText
						+ "')]")));

		// 再度クリックする
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", link);

		System.out.println("◎「" + menuLabel + "」メニューから「" + linkText + "」をクリックしました。");
	}

	/**
	 * 
	 * ⑧10/24 指定したりんくをクリックする
	 * 
	 * @param href hrefの値
	 * @author 河島
	 */
	public static void clickLinkByHref(String href) {
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(15));

		// 「href」が完全一致または部分一致するリンクを待機
		WebElement link = wait.until(ExpectedConditions.elementToBeClickable(
				By.cssSelector("a[href$='" + href + "']")));

		// JavaScriptでクリック
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", link);

		System.out.println("◎リンク「" + href + "」をクリックしました。");
	}
}