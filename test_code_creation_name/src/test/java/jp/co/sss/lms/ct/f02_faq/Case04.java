package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jp.co.sss.lms.ct.util.WebDriverUtils;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-save-password-bubble");
		options.addArguments("--disable-password-generation");
		options.addArguments("--disable-notifications");
		options.addArguments("--disable-infobars");
		webDriver = new ChromeDriver(options);

	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// TODO ここに追加
		// ケース04 - No.01 トップページにアクセス
		goTo("http://localhost:8080/lms/");

		assertTrue(isTitle("ログイン | LMS"),
				"タイトルが正しく表示されていることを確認する。");
		assertTrue(isElementPresentById("loginId"),
				"ログインID入力欄が表示されていることを確認する。");
		assertTrue(isElementPresentById("password"),
				"パスワード入力欄が表示されていることを確認する。");
		assertTrue(isElementPresentByCssSelector("input[type='submit']"),
				"ログインボタンが表示されていることを確認する。");

		getEvidence(new Object() {
		}, "ケース04_よくある質問への遷移_初期画面");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加

		// ケース04 - No.02 コース詳細画面への遷移（ログイン）
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA011");
		webDriver.findElement(By.cssSelector("input[type='submit']")).click();

		assertTrue(isTitle("コース詳細 | LMS"),
				"タイトルが正しく表示されていることを確認する。");

		getEvidence(new Object() {
		}, "ケース04_よくある質問への遷移_コース詳細画面");

	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// TODO ここに追加

		// ケース04 - No.03 「ヘルプ」リンク押下
		webDriver.findElement(By.cssSelector("a.dropdown-toggle")).click();
		// 「ヘルプ」リンクが表示されるまで待機
		WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("ヘルプ"))).click();

		webDriver.findElement(By.linkText("ヘルプ")).click();

		// ケース04 - No.04 タイトル確認
		assertTrue(isTitle("ヘルプ | LMS"), "タイトルが正しく表示されていることを確認する。");

		// ケース04 - No.05 URL確認
		assertTrue(isUrlEndsWith("/help/index"),
				"「ヘルプ」URLを確認する");

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース04_よくある質問への遷移_ヘルプ画面");

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// TODO ここに追加

		// ケース04 - No.06 「よくある質問」リンク押下
		String originalWindow = webDriver.getWindowHandle();
		webDriver.findElement(By.cssSelector("a[href='/faq']")).click();

		// ケース04 - No.0 タイトル確認
		assertTrue(isTitle("よくある質問 | LMS"),
				"タイトルが正しく表示されていることを確認する。");

		// ケース04 - No.0 URL確認
		assertTrue(isUrlEndsWith("/faq/index"),
				"「よくある質問」URLを確認する");

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース04_よくある質問への遷移");

		// 別タブを閉じて元のヘルプ画面に戻る
		WebDriverUtils.closeAndReturnWindow(originalWindow);

	}
}