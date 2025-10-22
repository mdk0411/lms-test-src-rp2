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

/**
 * 結合テスト ログイン機能①
 * ケース01
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース01 ログイン画面への遷移")
public class Case01 {

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

		// ケース01 - No.01 トップページにアクセス
		goTo("http://localhost:8080/lms/");

		// ケース01 - No.02 画面タイトル確認
		assertEquals("ログイン | LMS", webDriver.getTitle(),
				"ログイン画面が正しく表示されていることを確認する。");

		// ケース01 - No.03 ログインフォームの要素を確認
		assertTrue(isElementPresentById("loginId"),
				"ログインID入力欄が表示されていることを確認する。");
		assertTrue(isElementPresentById("password"),
				"パスワード入力欄が表示されていることを確認する。");
		assertTrue(isElementPresentByCssSelector("input[type='submit']"),
				"ログインボタンが表示されていることを確認する。");

		// ケース01 - No.04 HTTPエラー（404,500など）が発生していないことを確認する。

		// エビデンスを取得
		getEvidence(new Object() {
		}, "ケース01_ログイン画面への遷移");

	}

}
