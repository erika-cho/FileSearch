package jp.co.libgate.training;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * TODO:コメントの記入例を確認した後改めて
 *
 */
public class Main {
	public static void main(String[] args) {
		String get = new String();
		// ユーザーホーム以下を対象に検索
		String path = new File(".").getAbsoluteFile().getParent();
		path = System.getProperty("user.home");
		File fpath = new File(path);
		System.out.println("検索するファイル名を入力して下さい");
		try (BufferedReader br = new BufferedReader(new InputStreamReader (System.in))) {
			while ((get = br.readLine()) != null) {
				// 空もしくは3文字以下の場合エラー
				if (get.equals("") || get.length() < 3) {
					System.err.println("エラー：3文字以上で入力して下さい");
				} else {
					System.out.println("検索中...");
					readFolder(fpath, get);
				}
			}
		} catch (IOException err) {
			System.err.println(err);
		}
	}

	private static void readFolder(File dir, String get) {
		// ユーザーホーム内のディレクトリ、ファイルを取得
		File[] flist = dir.listFiles();
		for (File dirs : flist) {
			if (dirs.isDirectory() && !dirs.isHidden()) {
				//　ディレクトリの場合再帰、取得したディレクトリはreadFolderへ
				findFolder(dirs, get);
				readFolder(dirs, get);
			} else if (dirs.isFile() && !dirs.isHidden()) {
				//　隠しファイルを除くファイルの場合はreadFolderに値を渡す
				findFolder(dirs, get);
			} else if (!dirs.exists()) {
				// 検索対象がなくなった場合終了
				System.out.println("検索が終了しました。");
				System.exit(0);
			}
		}
	}

	public static void findFolder(File dirs, String filename) {
		String str = dirs.getName();
		String regex = filename;
		Pattern ptn = Pattern.compile(regex);
		Matcher mat = ptn.matcher(str);
		// 入力した文字列と一致するファイルのパス名を表示
		if (mat.find()){
		  System.out.println(dirs.getAbsolutePath());
		}
	}
}
