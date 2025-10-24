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
 * ケース05
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース05 キーワード検索 正常系")
public class Case05 {

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

		// ケース05 - No.01 トップページにアクセス
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

		// ケース05 - No.02 コース詳細画面への遷移（ログイン）
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

		// ケース05 - No.03「機能」のプルダウンを開き、「ヘルプ」のリンクを押下する。
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

		// ケース05 - No.04 「よくある質問」リンクを押下
		clickLinkByHref("/lms/faq");

		// ケース05 - No.05 別タブで開かれるか確認する 
		String newWindow = switchToNewWindow();

		// ケース05 - No.06 タイトル確認
		assertTrue(isTitle("よくある質問 | LMS"));

		// ケース05 - No.07 URL確認
		assertTrue(isUrlEndsWith("/faq"));

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース05_キーワード検索_正常系_よくある質問画面");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 キーワード検索で該当キーワードを含む検索結果だけ表示")
	void test05() {
		// TODO ここに追加

		// ケース05 - No.08 よくある質問画面で「キャンセル」を入力
		typeText(By.id("form"), "キャンセル", 10);
		assertEquals("キャンセル", getInputValue(By.name("keyword")));

		// ケース05 - No.09 検索実行
		clickElement(By.cssSelector("input[type='submit']"), 5);
		assertTrue(isTextPresent("キャンセル"), "検索結果に「キャンセル」を含む質問が表示されること");

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース05_キーワード検索_正常系_検索結果確認①");

		// ケース05 - No.10 クリア実行
		clickElement(By.xpath("//input[@value='クリア']"), 5);
		assertEquals("", getInputValue(By.name("keyword")), "入力欄が空になっていることを確認");

		// 念のため違うキーワードでも検索
		typeText(By.id("form"), "申し込み", 10);
		assertEquals("申し込み", getInputValue(By.name("keyword")));

		// ケース05 - No.09 検索実行
		clickElement(By.cssSelector("input[type='submit']"), 5);
		assertTrue(isTextPresent("申し込み"), "検索結果に「申し込み」を含む質問が表示されること");

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース05_キーワード検索_正常系_検索結果確認②");

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 「クリア」ボタン押下で入力したキーワードを消去")
	void test06() {
		// TODO ここに追加

		// ケース05 - No.10 クリア実行
		clickElement(By.xpath("//input[@value='クリア']"), 5);
		assertEquals("", getInputValue(By.name("keyword")), "入力欄が空になっていることを確認");

		getEvidence(new Object() {
		}, "ケース05_キーワード検索_正常系_クリア確認");
	}
}
