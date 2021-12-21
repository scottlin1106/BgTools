package com.bgtools;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;



public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(500,400);
        borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(200, 200, 200), null, null)));

        TabPane tabPane = new TabPane();
//        tabPane.setPrefSize(400,300);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setBackground(new Background(new BackgroundFill(Color.rgb(200, 200, 200), null, null)));

        Tab linuxTab = new Tab("Linux指令");
        Tab dfStateTab = new Tab("代付狀態變更");
        Tab logTab = new Tab("Log相关指令");
        Tab hostTab = new Tab("主機測試");
        Scene scene = new Scene(borderPane, 380, 280);

        //分頁設定
        GridPane gridPg1 = new GridPane();
        gridPg1.setPadding(new Insets(10, 10, 10, 10));
        gridPg1.setVgap(10);
        gridPg1.setHgap(10);
        VBox vBoxPg1 = new VBox();
        final ToggleGroup toggleGroupPg1 = new ToggleGroup();

        GridPane gridPg2 = new GridPane();
        gridPg2.setPadding(new Insets(10, 10, 10, 10));
        gridPg2.setVgap(10);
        gridPg2.setHgap(10);
        VBox vBoxPg2 = new VBox();
        final ToggleGroup toggleGroupPg2 = new ToggleGroup();

        GridPane gridPg3 = new GridPane();
        gridPg3.setPadding(new Insets(10, 10, 10, 10));
        gridPg3.setVgap(10);
        gridPg3.setHgap(10);
        VBox vBoxPg3 = new VBox();
        final ToggleGroup toggleGroupPg3 = new ToggleGroup();

        GridPane gridPg4 = new GridPane();
        gridPg4.setPadding(new Insets(10, 10, 10, 10));
        gridPg4.setVgap(10);
        gridPg4.setHgap(10);
        VBox vBoxPg4 = new VBox();
        final ToggleGroup toggleGroupPg4 = new ToggleGroup();

        //第一頁簽
        Pane tabPane1 = new Pane();
        linuxTab.setContent(tabPane1);

        RadioButton sudoYRBPg1 = new RadioButton("是");
        sudoYRBPg1.setPrefSize(130, 10);
        sudoYRBPg1.setToggleGroup(toggleGroupPg1);
        RadioButton sudoNRBPg1 = new RadioButton("否");
        sudoNRBPg1.setPrefSize(130, 10);
        sudoNRBPg1.setToggleGroup(toggleGroupPg1);
        toggleGroupPg1.selectToggle(sudoYRBPg1);

        gridPg1.add(new Label("Sudo权限 ： "), 0, 0);
        gridPg1.add(sudoYRBPg1, 1, 0);
        gridPg1.add(sudoNRBPg1, 2, 0);

        gridPg1.add(new Label("资料夹名称 ： "), 0, 1);
        TextField folderName = new TextField();
        folderName.setMaxWidth(270);
        folderName.setText("Label");
        folderName.clear();
        gridPg1.add(folderName, 1, 1, 3, 1);

        gridPg1.add(new Label("Linux指令 ： "), 0, 2);
        Button paymentPathBtn = new Button("支付路径");
        paymentPathBtn.setPrefSize(130, 10);
        paymentPathBtn.setOnAction(actionEvent -> {
            uitl.copyStr("cd " + textPara.paymentStr, paymentPathBtn.getText(), folderName.getText(), sudoYRBPg1.isSelected());
        });

        Button autoPayPathBtn = new Button("代付路径");
        autoPayPathBtn.setPrefSize(130, 10);
        autoPayPathBtn.setOnAction(actionEvent -> {
            uitl.copyStr("cd " + textPara.autoStr, autoPayPathBtn.getText(), folderName.getText(), sudoYRBPg1.isSelected());
        });


        Button ipWhiteListPathBtn = new Button("ipWhiteList路径");
        ipWhiteListPathBtn.setPrefSize(130, 10);
        ipWhiteListPathBtn.setOnAction(actionEvent -> {
            uitl.copyStr("cd " + textPara.paymentClassesStr, ipWhiteListPathBtn.getText(), sudoYRBPg1.isSelected());
        });

        Button paymentCNAMEPathBtn = new Button("cName路径");
        paymentCNAMEPathBtn.setPrefSize(130, 10);
        paymentCNAMEPathBtn.setOnAction(actionEvent -> {
            uitl.copyStr("cd " + textPara.autoPayClassesStr, paymentCNAMEPathBtn.getText(), sudoYRBPg1.isSelected());
        });
        Button paymentSumBtn = new Button("支付统计");
        paymentSumBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        paymentSumBtn.setOnAction(actionEvent -> {
            uitl.copyStr(textPara.pstsStr, paymentCNAMEPathBtn.getText(), sudoYRBPg1.isSelected());
        });

        Button paymentChkBtn = new Button("支付档案");
        paymentChkBtn.setPrefSize(130, 10);
        paymentChkBtn.setOnAction(actionEvent -> {
            uitl.copyStr(textPara.lsStr + textPara.paymentStr, paymentChkBtn.getText() + "检查指令", folderName.getText(), sudoYRBPg1.isSelected());
        });

        Button autoPayChkBtn = new Button("代付档案");
        autoPayChkBtn.setPrefSize(130, 10);
        autoPayChkBtn.setOnAction(actionEvent -> {
            uitl.copyStr(textPara.lsStr + textPara.autoStr, paymentChkBtn.getText() + "检查指令", folderName.getText(), sudoYRBPg1.isSelected());
        });

        Button chkIpWhiteListBtn = new Button("ipWhiteList");
        chkIpWhiteListBtn.setPrefSize(130, 10);
        chkIpWhiteListBtn.setOnAction(actionEvent -> {
            uitl.copyStr(textPara.lsStr + textPara.paymentClassesStr + "ipWhiteList.properties \n", chkIpWhiteListBtn.getText() + ".properties 检查指令", sudoYRBPg1.isSelected());
        });

        Button chkPaymentCNAMEBtn = new Button("paymentCNAME");
        chkPaymentCNAMEBtn.setPrefSize(130, 10);
        chkPaymentCNAMEBtn.setOnAction(actionEvent -> {
            uitl.copyStr(textPara.lsStr + textPara.autoPayClassesStr + "paymentCNAME.properties\n", chkPaymentCNAMEBtn.getText() + ".properties 检查指令", sudoYRBPg1.isSelected());
        });

        gridPg1.add(paymentPathBtn, 1, 2);
        gridPg1.add(autoPayPathBtn, 2, 2);


        gridPg1.add(ipWhiteListPathBtn, 1, 3);
        gridPg1.add(paymentCNAMEPathBtn, 2, 3);
        gridPg1.add(paymentSumBtn, 1, 4);

        gridPg1.add(new Label("上版确认 ： "), 0, 4);
        gridPg1.add(paymentChkBtn, 1, 4);
        gridPg1.add(autoPayChkBtn, 2, 4);
        gridPg1.add(chkIpWhiteListBtn, 1, 5);
        gridPg1.add(chkPaymentCNAMEBtn, 2, 5);

        vBoxPg1.getChildren().addAll(gridPg1);
        linuxTab.setContent(vBoxPg1);

        //第二頁簽
        Pane tabPane2 = new Pane();
        dfStateTab.setContent(tabPane2);

        RadioButton uatRBPg2 = new RadioButton("UAT");
        uatRBPg2.setPrefSize(130, 10);
        uatRBPg2.setToggleGroup(toggleGroupPg2);

        RadioButton prdRBPg2 = new RadioButton("PRD");
        prdRBPg2.setPrefSize(130, 10);
        prdRBPg2.setToggleGroup(toggleGroupPg2);
        toggleGroupPg2.selectToggle(prdRBPg2);
        toggleGroupPg2.selectToggle(prdRBPg2);

        gridPg2.add(new Label("环境选择 ： "), 0, 0);
        gridPg2.add(uatRBPg2, 1, 0);
        gridPg2.add(prdRBPg2, 2, 0);
//
        gridPg2.add(new Label("代付名称 ： "), 0, 1);
        TextField dfName = new TextField();
        dfName.setMaxWidth(270);
        dfName.setText("Label");
        dfName.clear();
        gridPg2.add(dfName, 1, 1, 3, 1);

        PropertiesManager propertiesManager = new PropertiesManager();

        gridPg2.add(new Label("代付資訊 ： "), 0, 2);
        TextArea dfData = new TextArea();
        dfData.setEditable(true);
        dfData.setWrapText(true);
        dfData.setMaxHeight(100);
        dfData.setMaxWidth(270);
        dfData.setPromptText("Commpay or extMap 資訊");
        gridPg2.add(dfData, 1, 2, 3, 1);

        gridPg2.add(new Label("狀態變更 ： "), 0, 3);
        Button sucBtn = new Button("成功");
        sucBtn.setPrefSize(130, 10);
        sucBtn.setOnAction(actionEvent -> {
            String url = "";
            if (uatRBPg2.isSelected())
                url = textPara.uatUrl + textPara.dfSuccess;
            else
                url = textPara.prdUrl + textPara.dfSuccess;

            String bodyStr = okHttp3Uitl.formPostSkipSSL(url, uitl.dfOutStr(dfData.getText(), dfName.getText()));
            JSONObject jsObj = JSONObject.fromObject(bodyStr);
            if ("1".equalsIgnoreCase(jsObj.optString("result"))) {
                uitl.showaAlert("请求返回", "代付状态变更成功，请客户刷新页面查看", Alert.AlertType.INFORMATION);
            } else {
                uitl.showaAlert(jsObj.optJSONObject("error").optString("message"), jsObj.optJSONObject("error").optString("reason"), Alert.AlertType.ERROR);
            }
        });

        Button failBtn = new Button("失敗");
        failBtn.setPrefSize(130, 10);
        failBtn.setOnAction(actionEvent -> {
            String url = "";
            if (uatRBPg2.isSelected())
                url = textPara.uatUrl + textPara.dfFailure;
            else
                url = textPara.prdUrl + textPara.dfFailure;

            String bodyStr = okHttp3Uitl.formPostSkipSSL(url, uitl.dfOutStr(dfData.getText(), dfName.getText()));
            JSONObject jsObj = JSONObject.fromObject(bodyStr);

            if ("0".equalsIgnoreCase(jsObj.optString("result"))) {
                uitl.showaAlert("请求返回", jsObj.optJSONObject("error").optString("message"), Alert.AlertType.INFORMATION);
            } else {
                uitl.showaAlert("请检查报文内容", bodyStr, Alert.AlertType.ERROR);
            }
        });
        gridPg2.add(sucBtn, 1, 3);
        gridPg2.add(failBtn, 2, 3);

        vBoxPg2.getChildren().addAll(gridPg2);
        dfStateTab.setContent(vBoxPg2);

        //第三頁簽
        Pane tabPane3 = new Pane();
        logTab.setContent(tabPane3);

        RadioButton uatRBPg3 = new RadioButton("UAT");
        uatRBPg3.setPrefSize(130, 10);
        uatRBPg3.setToggleGroup(toggleGroupPg3);

        RadioButton prdRBPg3 = new RadioButton("PRD");
        prdRBPg3.setPrefSize(130, 10);
        prdRBPg3.setToggleGroup(toggleGroupPg3);
        toggleGroupPg3.selectToggle(prdRBPg3);

        gridPg3.add(new Label("环境选择 ： "), 0, 0);
        gridPg3.add(uatRBPg3, 1, 0);
        gridPg3.add(prdRBPg3, 2, 0);

        gridPg3.add(new Label("查詢日期 ： "), 0, 2);
        DatePicker checkInDatePickerPg3 = new DatePicker();
        checkInDatePickerPg3.setValue(LocalDate.now());
        checkInDatePickerPg3.setMaxHeight(100);
        checkInDatePickerPg3.setMaxWidth(270);
        gridPg3.add(checkInDatePickerPg3, 1, 2, 3, 1);

        gridPg3.add(new Label("關鍵字 ： "), 0, 3);
        TextField keyWord = new TextField();
        keyWord.setMaxWidth(270);
        keyWord.setText("Label");
        keyWord.clear();
        gridPg3.add(keyWord, 1, 3, 3, 1);

        gridPg3.add(new Label("查詢行數 ： "), 0, 4);
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        choiceBox.setValue(3);
        gridPg3.add(choiceBox, 1, 4, 3, 1);

        Button linuxLogBtn = new Button("关键字查询");
        linuxLogBtn.setPrefSize(360, 10);
        gridPg3.add(linuxLogBtn, 0, 5, 4, 1);
        linuxLogBtn.setOnAction(actionEvent -> {
            String logSearchStr = uitl.setLogStr("" + checkInDatePickerPg3.getValue(), keyWord.getText(), choiceBox.getValue().toString(), uatRBPg3.isSelected());
            if (!"".equals(logSearchStr))
                uitl.copyStr(logSearchStr, "Log " + linuxLogBtn.getText());
        });

        Button linuxLookBtn = new Button("查看指令");
        linuxLookBtn.setPrefSize(360, 10);
        gridPg3.add(linuxLookBtn, 0, 6, 4, 1);
        linuxLookBtn.setOnAction(actionEvent -> {
            String logSearchStr = textPara.logStr;
            if (prdRBPg3.isSelected()) {
                logSearchStr += "." + checkInDatePickerPg3.getValue() + ".log";
            }
            System.out.println("logSearchStr = " + logSearchStr);
            uitl.copyStr(logSearchStr, "Log " + linuxLookBtn.getText());
        });


        gridPg3.add(new Label("Log操作 ： "), 0, 7);
        Button copyLogBtn = new Button("复制");
        copyLogBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        copyLogBtn.setOnAction(actionEvent -> {
            String copyStr = textPara.sudoStr + "cp -r /usr/local/tomcat702/logs/catalina.out." + checkInDatePickerPg3.getValue() + ".log /tmp/";
            uitl.copyStr(copyStr, "Log複製指令 ");
        });

        Button delLogBtn = new Button("删除");
        delLogBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        delLogBtn.setOnAction(actionEvent -> {
            String copyStr = textPara.sudoStr + "rm -f /tmp/catalina.out." + checkInDatePickerPg3.getValue() + ".log";
            uitl.copyStr(copyStr, "Log刪除指令 ");
        });
        gridPg3.add(copyLogBtn, 1, 7);
        gridPg3.add(delLogBtn, 2, 7);

        vBoxPg3.getChildren().addAll(gridPg3);
        logTab.setContent(vBoxPg3);

        //第四頁簽
        Pane tabPane4 = new Pane();
        hostTab.setContent(tabPane4);
        gridPg4.add(new Label("支付主機 ： "), 0, 0);
        ChoiceBox paymentCB = new ChoiceBox();
        paymentCB.setMaxWidth(220);

        String[] paymentIp = propertiesManager.getProperty("bgPayment").toString().split(",");
        for (String key : paymentIp) {
            paymentCB.getItems().addAll(key);
        }
        gridPg4.add(paymentCB, 1, 0, 2, 1);

        Button paymentHostBtn = new Button("测试");
        gridPg4.add(paymentHostBtn, 3, 0, 2, 1);
        paymentHostBtn.setOnAction(actionEvent -> {
            uitl.showaAlert("伺服器測試結果", uitl.hostText(paymentCB.getValue().toString(), propertiesManager.getProperty("masterAuth").toString(), "1"), Alert.AlertType.INFORMATION);
        });

        gridPg4.add(new Label("代付主機 ： "), 0, 1);
        ChoiceBox autoPayCB = new ChoiceBox();
        autoPayCB.setMaxWidth(220);

        String[] autoPayIp = propertiesManager.getProperty("bgAutopay").toString().split(",");
        for (String key : autoPayIp) {
            autoPayCB.getItems().addAll(key);
        }
        gridPg4.add(autoPayCB, 1, 1, 2, 1);

        Button autoPayHostBtn = new Button("测试");
        gridPg4.add(autoPayHostBtn, 3, 1, 2, 1);
        autoPayHostBtn.setOnAction(actionEvent -> {
            uitl.showaAlert("伺服器測試結果", uitl.hostText(autoPayCB.getValue().toString(), propertiesManager.getProperty("masterAuth").toString(), "1"), Alert.AlertType.INFORMATION);
        });

        gridPg4.add(new Label("域名测试 ： "), 0, 2);
        TextField hostTest = new TextField();
        hostTest.setMaxWidth(220);
        hostTest.setPromptText("測試主機連線狀態");
        gridPg4.add(hostTest, 1, 2, 2, 1);

        Button hostTestBtn = new Button("测试");
        gridPg4.add(hostTestBtn, 3, 2, 2, 1);
        hostTestBtn.setOnAction(event -> {
            System.out.println(" = " + uitl.getHostIp(hostTest.getText()));
            uitl.showaAlert("伺服器測試結果", uitl.hostText(uitl.getHostIp(hostTest.getText()), propertiesManager.getProperty("masterAuth").toString(), "1"), Alert.AlertType.INFORMATION);
        });

        gridPg4.add(new Label("取得IP ： "), 0, 3);

        TextField hostText = new TextField();
        hostText.setMaxWidth(220);
        hostText.setPromptText("輸入網址獲得IP");
        gridPg4.add(hostText, 1, 3, 2, 1);

        Button searchIpBtn = new Button("查詢");
        gridPg4.add(searchIpBtn, 3, 3, 2, 1);
        searchIpBtn.setOnAction(event -> {
            uitl.showaAlert("测试结果", "服务器IP为 ：" + uitl.getHostIp(hostText.getText()), Alert.AlertType.INFORMATION);
        });

        vBoxPg4.getChildren().addAll(gridPg4);
        hostTab.setContent(vBoxPg4);

//視窗頁面配置
        tabPane.getTabs().addAll(linuxTab, dfStateTab, logTab, hostTab);

        Label versionText = new Label("V1.0");
        HBox hBox = new HBox();
        hBox.getChildren().add(versionText);
        hBox.setAlignment(Pos.CENTER_RIGHT);

        borderPane.setBottom(hBox);
        borderPane.setCenter(tabPane);

        primaryStage.getIcons().add(new Image("logo-bg.png"));
        primaryStage.setTitle("BigGame Payment Tools");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}