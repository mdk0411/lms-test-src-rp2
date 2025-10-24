package jp.co.sss.lms.ct.f02_faq;

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
 * 結合テスト よくある質問機能
 * ケース06
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース06 カテゴリ検索 正常系")
public class Case06 {

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

		// ケース06 - No.01 トップページにアクセス
		goTo("http://localhost:8080/lms/");
		assertTrue(isTitle("ログイン | LMS"));
		assertTrue(isElementPresentById("loginId"));
		assertTrue(isElementPresentById("password"));
		assertTrue(isElementPresentByCssSelector("input[type='submit']"));
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加

		// ケース06 - No.02 コース詳細画面への遷移（ログイン）
		typeText(By.id("loginId"), "StudentAA01", 10);
		typeText(By.id("password"), "StudentAA011", 5);
		clickElement(By.cssSelector("input[type='submit']"), 5);
		waitForTitle("コース詳細 | LMS", 10);
		assertTrue(isTitle("コース詳細 | LMS"));

	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// TODO ここに追加

		// ケース06 - No.03「機能」のプルダウンを開き、「ヘルプ」のリンクを押下する。
		clickDropDownLink("機能", "ヘルプ");
		assertTrue(isTitle("ヘルプ | LMS"));
		assertTrue(isUrlEndsWith("/help"));

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// TODO ここに追加

		String originalWindow = webDriver.getWindowHandle();

		// ケース06 - No.04 「よくある質問」リンクを押下
		clickLinkByHref("/lms/faq");

		String newWindow = switchToNewWindow();
		assertTrue(isTitle("よくある質問 | LMS"));
		assertTrue(isUrlEndsWith("/faq"));

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース06_カテゴリ検索_正常系_よくある質問画面");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 カテゴリ検索で該当カテゴリの検索結果だけ表示")
	void test05() {
		// TODO ここに追加

		// ケース06 - No.05 【研修関係】などのリンクを押下し、カテゴリ検索を行う。
		clickElement(By.linkText("【研修関係】"), 10);

		// ケース06 - No.05 期待値：該当カテゴリの質問だけが表示されることを確認
		assertTrue(isTextPresent("研修関係"),
				"該当カテゴリの検索結果だけが表示されていること");

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース06_カテゴリ検索_正常系_検索結果確認");

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 検索結果の質問をクリックしその回答を表示")
	void test06() {
		// TODO ここに追加

		// ケース06 - No.06 検索結果の質問を押下する。
		clickElement(By.cssSelector("dt"), 5);

		// ケース06 - No.06 期待値：押下した質問の回答が表示されること。
		assertTrue(isTextPresent("A."), "押下した質問の回答が表示されること");

		//　エビデンス取得
		getEvidence(new Object() {
		}, "ケース06_カテゴリ検索_正常系_回答表示確認");
	}

}
