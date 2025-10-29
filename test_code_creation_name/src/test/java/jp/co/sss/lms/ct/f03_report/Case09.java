package jp.co.sss.lms.ct.f03_report;

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
 * 結合テスト レポート機能
 * ケース09
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース09 受講生 レポート登録 入力チェック")
public class Case09 {

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
		// ケース09 - No.01 トップページにアクセス
		checkTopPage();

	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加

		// ケース09 - No.02 初回ログイン済みの受講生ユーザーでログイン
		login("StudentAA01", "StudentAA011");
		checkCourseDetail();

	}

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test03() {
		// TODO ここに追加

		// ケース09 - No.03 上部メニューの「ようこそ○○さん」リンクを押下
		clickWelcomeGoToUserDetail();

		// タイトル確認
		waitForTitle("ユーザー詳細", 10);
		assertTrue(isTitle("ユーザー詳細"));

		// URL確認
		assertTrue(isUrlEndsWith("/user/detail"));
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 該当レポートの「修正する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// TODO ここに追加

		// ケース09 - No.04 該当レポートの「修正する」ボタンを押下する。
		clickJs(By.xpath("//tr[td[contains(text(),'週報【デモ】')]]//input[@value='修正する']"));
		// タイトルとURL確認
		waitForTitle("レポート登録 | LMS", 10);
		assertTrue(isTitle("レポート登録 | LMS"));
		assertTrue(isUrlEndsWith("/report/regist"));

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しエラー表示：学習項目が未入力")
	void test05() {
		// TODO ここに追加

		checkLearningTopicValid();

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース09_受講生レポート登録_不適切_学習項目が未入力");
	}

	@Test
	@Order(6)
	@DisplayName("テスト06 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：理解度が未入力")
	void test06() {
		// TODO ここに追加

		checkCompreValid();

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース09_受講生レポート登録_不適切_理解度が未入力");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が数値以外")
	void test07() {
		// TODO ここに追加
		checkGoalNotNumericValid();

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース09_受講生レポート登録_不適切_目標の達成度が数値以外");

	}

	@Test
	@Order(8)
	@DisplayName("テスト08 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度が範囲外")
	void test08() {
		// TODO ここに追加

		checkGoalOutOfRangeValid();

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース09_受講生レポート登録_不適切_目標の達成度が範囲外");

	}

	@Test
	@Order(9)
	@DisplayName("テスト09 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：目標の達成度・所感が未入力")
	void test09() {
		// TODO ここに追加
		checkGoalAndCommentEmptyValid();
		// エビデンス取得
		scrollTo("500");
		getEvidence(new Object() {
		}, "ケース09_受講生レポート登録_不適切_目標の達成度・所感が未入力");

	}

	@Test
	@Order(10)
	@DisplayName("テスト10 不適切な内容で修正して「提出する」ボタンを押下しエラー表示：桁数チェック（2000文字）")
	void test10() {
		// TODO ここに追加

		checkOver2000CharsValid();

		// エビデンス取得
		scrollTo("500");
		getEvidence(new Object() {
		}, "ケース09_受講生レポート登録_不適切_所感が20000文字入力されている");

	}

}
