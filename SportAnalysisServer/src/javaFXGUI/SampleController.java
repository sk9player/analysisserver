package javaFXGUI;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import setting.SettingValues;
import data.DataManager;

public class SampleController implements Runnable, Initializable {



	/**
	 * サーバ内部からRunnableインターフェースの機能で呼び出されるメソッド
	 */
	@Override
	public void run() {
		System.out.println("外部呼出し");
	}

	/**
	 * 初期に呼ばれるメソッド
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeSettingTable();

	}



/*---------------------------------------- 設定--------------------------------------*/

	@FXML
	private TableView<SettingValueTableData> setting_value_table = new TableView<SettingValueTableData>();
	@FXML
	private TableColumn<SettingValueTableData,Double> setting_value = new TableColumn<SettingValueTableData,Double>();
	@FXML
	private TableColumn<SettingValueTableData,String> setting_name = new TableColumn<SettingValueTableData,String>();
	@FXML
	private TableColumn<SettingValueTableData,String> new_setting_value = new TableColumn<SettingValueTableData,String>();

	/**
	 * 設定変更フラグ
	 */
	private boolean new_setting_flg = false;

	/**
	 * 設定更新ボタン
	 */
	@FXML
	public void SettingButtonClicked() {
		System.out.println("hello" + SettingValues.ACC_DATA_KIND);

		int setting_num = SettingValues.setting_names.length;

		if (new_setting_flg) {
			for (int j = 0; j < setting_num; j++) {
				String new_val=setting_value_table.getItems().get(j).new_valueProperty().get();
				if (new_val != null) {
					SettingValues.setSettingValues(j,Double.valueOf(new_val));
				}
			}
		}

		for (int i = 0; i < setting_num; i++) {
			setting_value_table.getItems().set(
					i,
					new SettingValueTableData(SettingValues.setting_names[i],
							SettingValues.getSettingValues()[i],null));
		}

	}


	private void initializeSettingTable() {
		int setting_num = SettingValues.setting_names.length;
		setting_name
				.setCellValueFactory(new PropertyValueFactory<SettingValueTableData, String>(
						"name"));
		setting_value
				.setCellValueFactory(new PropertyValueFactory<SettingValueTableData, Double>(
						"value"));

		new_setting_value
				.setCellFactory(new Callback<TableColumn<SettingValueTableData, String>, TableCell<SettingValueTableData, String>>() {
					public TableCell<SettingValueTableData, String> call(
							TableColumn<SettingValueTableData, String> arg0) {
						return new TextFieldTableCell<SettingValueTableData, String>(
								new DefaultStringConverter());
					}
				});
		new_setting_value
				.setOnEditCommit(new EventHandler<CellEditEvent<SettingValueTableData, String>>() {
					@Override
					public void handle(
							CellEditEvent<SettingValueTableData, String> t) {
						new_setting_flg = true;
						((SettingValueTableData) t.getTableView().getItems()
								.get(t.getTablePosition().getRow()))
								.setNewValue(t.getNewValue());
					}
				});
		for (int i = 0; i < setting_num; i++) {
			setting_value_table.getItems().add(
					new SettingValueTableData(SettingValues.setting_names[i],
							SettingValues.getSettingValues()[i],null));
		}

	}
	/*---------------------------------------- 設定--------------------------------------*/

	@FXML ComboBox user_name = new ComboBox();


	@FXML
	public void userNameOnMouseClicked() {
		Set<String> users = DataManager.sensor_personal_map.keySet();
		user_name.getItems().clear();
		user_name.getItems().addAll(users);

	}

	@FXML
	public void userNameOnAction() {
		if(user_name.getValue()!=null)
			System.out.println(DataManager.sensor_personal_map.get(user_name.getValue()).act_para.toString());

	}




}
