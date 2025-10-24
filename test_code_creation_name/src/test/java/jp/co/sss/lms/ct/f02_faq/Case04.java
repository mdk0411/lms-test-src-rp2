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

		assertTrue(isTitle("ログイン | LMS"));
		assertTrue(isElementPresentById("loginId"));
		assertTrue(isElementPresentById("password"));
		assertTrue(isElementPresentByCssSelector("input[type='submit']"));

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

		assertTrue(isTitle("コース詳細 | LMS"));
		getEvidence(new Object() {
		}, "ケース04_よくある質問への遷移_コース詳細画面");

	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
		// TODO ここに追加

		// ケース04 - No.03「機能」のプルダウンを開き、「ヘルプ」のリンクを押下する。
		clickDropDownLink("機能", "ヘルプ");

		// ケース04 - No.04 タイトル確認
		assertTrue(isTitle("ヘルプ | LMS"));

		// ケース04 - No.05 URL確認
		assertTrue(isUrlEndsWith("/help"));

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース04_よくある質問への遷移_ヘルプ画面");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
		// TODO ここに追加

		// ケース04 - No.06 「よくある質問」リンク押下前に現在のウィンドウを保持
		// 別タブで開いた場合に、元に戻るためのハンドルとして使用します
		String originalWindow = webDriver.getWindowHandle();

		// ケース04 - No.06 「よくある質問」リンク押下
		clickLinkByHref("/lms/faq");

		// ケース04 - No.07 別タブで開かれるか確認 
		String newWindow = switchToNewWindow();

		// ケース04 - No.08 タイトル確認
		assertTrue(isTitle("よくある質問 | LMS"));

		// ケース04 - No.09 URL確認
		assertTrue(isUrlEndsWith("/faq"));

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース04_よくある質問画面への遷移");

		// タブの状態に応じて戻り処理を分岐
		if (!newWindow.equals(originalWindow)) {
			closeAndReturnWindow(originalWindow);
		} else {
			System.out.println("△「よくある質問」は同一タブで開かれたため、戻り処理をスキップします。");
		}
	}
}