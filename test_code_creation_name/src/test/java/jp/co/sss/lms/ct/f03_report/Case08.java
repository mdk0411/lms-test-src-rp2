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
 * ケース08
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース08 受講生 レポート修正(週報) 正常系")
public class Case08 {

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

		// ケース08 - No.01 トップページにアクセス
		checkTopPage();
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加

		// ケース08 - No.02 初回ログイン済みの受講生ユーザーでログイン
		login("StudentAA01", "StudentAA011");
		checkCourseDetail();
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 提出済の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// TODO ここに追加

		// ケース08 - No.03 「提出済」ステータスの「詳細」ボタンを押下する
		// ここでは「2022年10月2日(日)」行の「詳細」ボタンをクリックしている。
		clickJs(By.xpath("//tr[td[contains(text(),'2022年10月2日(日)')]]//input[@value='詳細']"));

		waitForTitle("セクション詳細 | LMS", 10);
		assertTrue(isTitle("セクション詳細 | LMS"));
		assertTrue(isUrlEndsWith("/section/detail"));

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース08_受講生レポート修正(週報)_正常系_セクション詳細画面");

	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「確認する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// TODO ここに追加

		// ケース08 - No.04 「提出済み週報を確認する」ボタンを押下する
		clickJs(By.xpath("//input[contains(@value,'提出済み週報')]"));

		// レポート登録画面に遷移したことを確認
		waitForTitle("レポート登録 | LMS", 10);
		assertTrue(isTitle("レポート登録 | LMS"));
		assertTrue(isUrlEndsWith("/report/regist"));

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース08_受講生レポート修正(週報)_正常系_レポート登録画面");

	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を修正して「提出する」ボタンを押下しセクション詳細画面に遷移")
	void test05() {
		// TODO ここに追加

		// ケース08 - No.05 報告内容を修正して「提出する」ボタンを押下する。
		typeTextJs(By.id("content_1"), "修正しました。", 10);

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース08_受講生レポート修正(週報)_正常系_報告内容の修正");

		// 「提出する」ボタンをクリック
		clickJs(By.cssSelector("button.btn.btn-primary"));

	}

	@Test
	@Order(6)
	@DisplayName("テスト06 上部メニューの「ようこそ○○さん」リンクからユーザー詳細画面に遷移")
	void test06() {
		// TODO ここに追加

		// ケース08 - No.06 上部メニューの「ようこそ○○さん」リンクを押下
		clickWelcomeGoToUserDetail();

		// タイトル確認
		waitForTitle("ユーザー詳細", 10);
		assertTrue(isTitle("ユーザー詳細"));

		// URL確認
		assertTrue(isUrlEndsWith("/user/detail"));

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース08_ケース08_受講生レポート修正(週報)_正常系_ユーザー詳細画面");
	}

	@Test
	@Order(7)
	@DisplayName("テスト07 該当レポートの「詳細」ボタンを押下しレポート詳細画面で修正内容が反映される")
	void test07() {
		// TODO ここに追加

		// ケース08 - No.07 該当レポートの「詳細」ボタンを押下する。
		clickJs(By.xpath("//tr[td[contains(text(),'週報【デモ】')]]//input[@value='詳細']"));

		// タイトルとURL確認
		waitForTitle("レポート詳細 | LMS", 10);
		assertTrue(isTitle("レポート詳細 | LMS"));
		assertTrue(isUrlEndsWith("/report/detail"));

		// ケース08 - No.08 修正内容の確認
		assertTrue(isTextPresent("修正しました。"));

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース08_受講生レポート修正(週報)_正常系_修正内容が反映されている");
	}
}
