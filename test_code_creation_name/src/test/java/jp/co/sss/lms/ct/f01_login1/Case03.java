package jp.co.sss.lms.ct.f01_login1;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;

/**
 * 結合テスト ログイン機能①
 * ケース03
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース03 受講生 ログイン 正常系")
public class Case03 {

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
		// ケース03 - No.01 トップページにアクセス
		goTo("http://localhost:8080/lms/");

		// ケース01 - No.02 画面タイトル確認
		assertEquals("ログイン | LMS", webDriver.getTitle(),
				"タイトルが正しく表示されていることを確認する。");

		assertTrue(isElementPresentById("loginId"),
				"ログインID入力欄が表示されていることを確認する。");
		assertTrue(isElementPresentById("password"),
				"パスワード入力欄が表示されていることを確認する。");
		assertTrue(isElementPresentByCssSelector("input[type='submit']"),
				"ログインボタンが表示されていることを確認する。");

		getEvidence(new Object() {
		}, "ケース03_受講生_ログイン_正常系_初期画面");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加

		// ケース03 - No.03 初回ログイン済みの受講生ユーザを入力し、「ログイン」ボタン押下。
		webDriver.findElement(By.id("loginId")).sendKeys("StudentAA01");
		webDriver.findElement(By.id("password")).sendKeys("StudentAA011");
		webDriver.findElement(By.cssSelector("input[type='submit']")).click();

		// ケース03 - No.04 画面タイトル確認
		assertTrue(isTitle("コース詳細 | LMS"),
				"タイトルが正しく表示されていることを確認する。");

		// ケース03 - No.05 URL確認
		assertTrue(isUrlEndsWith("/course/detail"),
				"コース詳細URLを確認する");

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース03_受講生_ログイン_正常系");
	}
}
