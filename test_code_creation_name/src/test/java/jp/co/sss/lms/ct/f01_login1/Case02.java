package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ResourceBundle;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jp.co.sss.lms.util.Constants;

/**
 * 結合テスト ログイン機能①
 * ケース02
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース02 受講生 ログイン 認証失敗")
public class Case02 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
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
		goTo("http://localhost:8080/lms/");

		assertEquals("ログイン | LMS", webDriver.getTitle(),
				"ログイン画面が正しく表示されていることを確認する。");

		assertTrue(isElementPresentById("loginId"),
				"ログインID入力欄が表示されていることを確認する。");
		assertTrue(isElementPresentById("password"),
				"パスワード入力欄が表示されていることを確認する。");
		assertTrue(isElementPresentByCssSelector("input[type='submit']"),
				"ログインボタンが表示されていることを確認する。");

		getEvidence(new Object() {
		}, "ケース02_受講生_ログイン_初期画面");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 DBに登録されていないユーザーでログイン")
	void test02() {
		// TODO ここに追加

		// ケース02 - No.03 DBに登録されていないユーザを入力し、「ログイン」ボタン押下。
		webDriver.findElement(By.id("loginId")).sendKeys("NotStudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA011");
		webDriver.findElement(By.cssSelector("input[type='submit']")).click();

		// ケース02 - No.04 エラーメッセージの表示確認
		WebElement error = webDriver.findElement(By.cssSelector("span.help-inline.error"));
		String actual = error.getText().trim();
		ResourceBundle bundle = ResourceBundle.getBundle("errors");
		String expected = "* " + bundle.getString(Constants.VALID_KEY_LOGIN);
		assertEquals(expected, actual,
				"エラーメッセージが正しく表示されていることを確認する。");

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース02_受講生_ログイン_認証失敗");
	}
}
