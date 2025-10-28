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
 * ケース07
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース07 受講生 レポート新規登録(日報) 正常系")
public class Case07 {

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

		// ケース07 - No.01 トップページにアクセス
		goTo("http://localhost:8080/lms/");
		assertTrue(isTitle("ログイン | LMS"));
		assertTrue(isElementPresentById("loginId"));
		assertTrue(isElementPresentById("password"));
		assertTrue(isElementPresentByCssSelector("input[type='submit']"));

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース07_受講生レポート新規登録(日報)_正常系_初期画面");
	}

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加

		// ケース07 - No.02 ログイン
		typeText(By.id("loginId"), "StudentAA01", 5);
		typeText(By.id("password"), "StudentAA011", 5);
		clickElement(By.cssSelector("input[type='submit']"), 5);
		assertTrue(isTitle("コース詳細 | LMS"));

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース07_受講生レポート新規登録(日報)_正常系_コース詳細画面");
	}

	@Test
	@Order(3)
	@DisplayName("テスト03 未提出の研修日の「詳細」ボタンを押下しセクション詳細画面に遷移")
	void test03() {
		// TODO ここに追加

		waitForTitle("コース詳細 | LMS", 5);

		// ケース07 - No.03 「未提出」ステータスの「詳細」ボタンをクリック
		clickDetail("未提出");

		// ケース07 - No.04 タイトル確認
		assertTrue(isTitle("セクション詳細 | LMS"));

		// ケース07 - No.05 URL確認
		assertTrue(isUrlEndsWith("/section/detail"));

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース07_受講生レポート新規登録(日報)_正常系_セクション詳細画面");
	}

	@Test
	@Order(4)
	@DisplayName("テスト04 「提出する」ボタンを押下しレポート登録画面に遷移")
	void test04() {
		// TODO ここに追加

		// ケース07 - No.06 「日報を提出する」ボタン押下
		goToReport();

		// ケース07 - No.07 タイトル確認
		assertTrue(isTitle("レポート登録 | LMS"));

		// ケース07 - No.08 URL確認
		assertTrue(isUrlEndsWith("/report/regist"));

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース07_受講生レポート新規登録(日報)_正常系_レポート登録画面");
	}

	@Test
	@Order(5)
	@DisplayName("テスト05 報告内容を入力して「提出する」ボタンを押下し確認ボタン名が更新される")
	void test05() {
		// TODO ここに追加

		// ケース07 - No.09 報告内容を入力
		typeTextJs(By.id("content_0"), "今日はできました。", 10);

		// 入力内容のエビデンス取得
		getEvidence(new Object() {
		}, "ケース07_受講生レポート新規登録(日報)_正常系_「今日はできました。」入力確認画面");

		// ケース07 - No.09 報告内容を入力して「提出する」ボタンを押下する。
		clickJs(By.cssSelector("button.btn.btn-primary"));

		// ケース07 - No.10 タイトル確認
		waitForTitle("セクション詳細 | LMS", 10);

		// ケース07 - No.10 URL確認
		waitForUrlContaints("/section/detail", 10);

		assertTrue(isTextPresent("提出済み日報【デモ】を確認する"));

		// エビデンス取得
		getEvidence(new Object() {
		}, "ケース07_受講生レポート新規登録(日報)_正常系_提出済み日報【デモ】を確認する");
	}
}
