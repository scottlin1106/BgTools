package com.bgtools;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;


public class HelloApplication extends Application {
    static String version = "V1.1.1";

    @Override
    public void start(Stage primaryStage) throws IOException {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(500, 400);
        borderPane.setBackground(new Background(new BackgroundFill(Color.rgb(200, 200, 200), null, null)));

        TabPane tabPane = new TabPane();
//        tabPane.setPrefSize(400,300);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setBackground(new Background(new BackgroundFill(Color.rgb(200, 200, 200), null, null)));

        Tab linuxTab = new Tab("Linux指令");
        Tab dfStateTab = new Tab("代付狀態變更");
        Tab logTab = new Tab("Log相关指令");
        Tab hostTab = new Tab("主機測試");
        Tab deCodeTab = new Tab("MD5原文解密");
        Scene scene = new Scene(borderPane, 400, 300);

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

        GridPane gridPg5 = new GridPane();
        gridPg5.setPadding(new Insets(10, 10, 10, 10));
        gridPg5.setVgap(10);
        gridPg5.setHgap(10);
        VBox vBoxPg5 = new VBox();
        final ToggleGroup toggleGroupPg5 = new ToggleGroup();

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
            Utils.copyStr("cd " + TextPara.paymentStr, paymentPathBtn.getText(), folderName.getText(), sudoYRBPg1.isSelected());
        });

        Button autoPayPathBtn = new Button("代付路径");
        autoPayPathBtn.setPrefSize(130, 10);
        autoPayPathBtn.setOnAction(actionEvent -> {
            Utils.copyStr("cd " + TextPara.autoStr, autoPayPathBtn.getText(), folderName.getText(), sudoYRBPg1.isSelected());
        });


        Button ipWhiteListPathBtn = new Button("ipWhiteList路径");
        ipWhiteListPathBtn.setPrefSize(130, 10);
        ipWhiteListPathBtn.setOnAction(actionEvent -> {
            Utils.copyStr("cd " + TextPara.paymentClassesStr, ipWhiteListPathBtn.getText(), sudoYRBPg1.isSelected());
        });

        Button paymentCNAMEPathBtn = new Button("cName路径");
        paymentCNAMEPathBtn.setPrefSize(130, 10);
        paymentCNAMEPathBtn.setOnAction(actionEvent -> {
            Utils.copyStr("cd " + TextPara.autoPayClassesStr, paymentCNAMEPathBtn.getText(), sudoYRBPg1.isSelected());
        });
        Button paymentSumBtn = new Button("支付统计");
        paymentSumBtn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        paymentSumBtn.setOnAction(actionEvent -> {
            Utils.copyStr(TextPara.pstsStr, paymentCNAMEPathBtn.getText(), sudoYRBPg1.isSelected());
        });

        Button paymentChkBtn = new Button("支付档案");
        paymentChkBtn.setPrefSize(130, 10);
        paymentChkBtn.setOnAction(actionEvent -> {
            Utils.copyStr(TextPara.lsStr + TextPara.paymentStr, paymentChkBtn.getText() + "检查指令", folderName.getText(), sudoYRBPg1.isSelected());
        });

        Button autoPayChkBtn = new Button("代付档案");
        autoPayChkBtn.setPrefSize(130, 10);
        autoPayChkBtn.setOnAction(actionEvent -> {
            Utils.copyStr(TextPara.lsStr + TextPara.autoStr, paymentChkBtn.getText() + "检查指令", folderName.getText(), sudoYRBPg1.isSelected());
        });

        Button chkIpWhiteListBtn = new Button("ipWhiteList");
        chkIpWhiteListBtn.setPrefSize(130, 10);
        chkIpWhiteListBtn.setOnAction(actionEvent -> {
            System.out.println("[ folderName.getText()) ] :" + folderName.getText());
            if (!Utils.isNullOrSpace(folderName.getText())) {
                Utils.copyStr("cat " + TextPara.paymentClassesStr + "ipWhiteList.properties | grep " + folderName.getText(), chkIpWhiteListBtn.getText() + ".properties 检查指令", sudoYRBPg1.isSelected());
            } else {
                Utils.copyStr(TextPara.lsStr + TextPara.paymentClassesStr + "ipWhiteList.properties \n", chkIpWhiteListBtn.getText() + ".properties 检查指令", sudoYRBPg1.isSelected());
            }
        });

        Button chkPaymentCNAMEBtn = new Button("paymentCNAME");
        chkPaymentCNAMEBtn.setPrefSize(130, 10);
        chkPaymentCNAMEBtn.setOnAction(actionEvent -> {
            if (!Utils.isNullOrSpace(folderName.getText())) {
                Utils.copyStr("cat " + TextPara.autoPayClassesStr + "paymentCNAME.properties | grep " + folderName.getText(), chkPaymentCNAMEBtn.getText() + ".properties 检查指令", sudoYRBPg1.isSelected());

            } else {
                Utils.copyStr(TextPara.lsStr + TextPara.autoPayClassesStr + "paymentCNAME.properties\n", chkPaymentCNAMEBtn.getText() + ".properties 检查指令", sudoYRBPg1.isSelected());
            }
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
                url = TextPara.uatUrl + TextPara.dfSuccess;
            else
                url = TextPara.prdUrl + TextPara.dfSuccess;

            String bodyStr = OkHttp3Util.formPost(url, Utils.dfOutStr(dfData.getText(), dfName.getText()));
            JSONObject jsObj = JSONObject.fromObject(bodyStr);
            if ("1".equalsIgnoreCase(jsObj.optString("result"))) {
                Utils.showaAlert("请求返回", "代付状态变更成功，请客户刷新页面查看", Alert.AlertType.INFORMATION);
            } else {
                Utils.showaAlert(jsObj.optJSONObject("error").optString("message"), jsObj.optJSONObject("error").optString("reason"), Alert.AlertType.ERROR);
            }
        });

        Button failBtn = new Button("失敗");
        failBtn.setPrefSize(130, 10);
        failBtn.setOnAction(actionEvent -> {
            String url = "";
            if (uatRBPg2.isSelected())
                url = TextPara.uatUrl + TextPara.dfFailure;
            else
                url = TextPara.prdUrl + TextPara.dfFailure;

            String bodyStr = OkHttp3Util.formPost(url, Utils.dfOutStr(dfData.getText(), dfName.getText()));
            JSONObject jsObj = JSONObject.fromObject(bodyStr);

            if ("0".equalsIgnoreCase(jsObj.optString("result"))) {
                Utils.showaAlert("请求返回", jsObj.optJSONObject("error").optString("message"), Alert.AlertType.INFORMATION);
            } else {
                Utils.showaAlert("请检查报文内容", bodyStr, Alert.AlertType.ERROR);
            }
        });
        gridPg2.add(sucBtn, 1, 3);
        gridPg2.add(failBtn, 2, 3);

        vBoxPg2.getChildren().addAll(gridPg2);
        dfStateTab.setContent(vBoxPg2);

        //第三頁簽
        Pane tabPane3 = new Pane();
        logTab.setContent(tabPane3);
//
        RadioButton sudoYRBPg3 = new RadioButton("是");
        sudoYRBPg3.setPrefSize(130, 10);
        sudoYRBPg3.setToggleGroup(toggleGroupPg3);
        RadioButton sudoNRBPg3 = new RadioButton("否");
        sudoNRBPg3.setPrefSize(130, 10);
        sudoNRBPg3.setToggleGroup(toggleGroupPg3);
        toggleGroupPg3.selectToggle(sudoYRBPg3);

        RadioButton uatRBPg3 = new RadioButton("UAT");
        uatRBPg3.setPrefSize(130, 10);
        uatRBPg3.setToggleGroup(toggleGroupPg3);

        gridPg3.add(new Label("Sudo权限 ： "), 0, 0);
        gridPg3.add(sudoYRBPg3, 1, 0);
        gridPg3.add(sudoNRBPg3, 2, 0);

        gridPg3.add(new Label("關鍵字 ： "), 0, 1);
        TextField keyWord = new TextField();
        keyWord.setMaxWidth(270);
        keyWord.setText("Label");
        keyWord.clear();
        gridPg3.add(keyWord, 1, 1, 6, 1);

/*
        gridPg3.add(new Label("查詢行數 ： "), 0, 2);
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        choiceBox.setValue(3);
        gridPg3.add(choiceBox, 1, 3, 6, 1);
*/
        Button keywordBtn = new Button("关键字查询");
        keywordBtn.setPrefSize(360, 10);
        gridPg3.add(keywordBtn, 0, 2, 6, 1);
        keywordBtn.setOnAction(actionEvent -> {
            if (!"".equals(keyWord.getText()) && keyWord.getText() != null) {
                String logSearchStr = Utils.setLogStr(sudoYRBPg3.isSelected(), "", keyWord.getText());
                System.out.println("logSearchStr = " + logSearchStr);
                if (!"".equals(logSearchStr))
                    Utils.copyStr(logSearchStr, "Log " + keywordBtn.getText());
                else {
                    Utils.showaAlert("提示", "请输入关键字", Alert.AlertType.INFORMATION);
                }
            }
            Utils.showaAlert("提示", "请输入关键字", Alert.AlertType.INFORMATION);
        });
/*
        Button keywordRowsBtn = new Button("关键字 + 指定行數查询");
        keywordRowsBtn.setPrefSize(360, 10);
        gridPg3.add(keywordRowsBtn, 0, 5, 6, 1);
        keywordRowsBtn.setOnAction(actionEvent -> {
            if (!"".equals(keyWord.getText()) && keyWord.getText() != null) {
                String logSearchStr = Utils.setLogStr(sudoYRBPg3.isSelected(), "", keyWord.getText(), choiceBox.getValue().toString());
                System.out.println("logSearchStr = " + logSearchStr);
                if (!"".equals(logSearchStr))
                    Utils.copyStr(logSearchStr, "Log " + keywordBtn.getText());
                else {
                    Utils.showaAlert("提示", "请输入关键字", Alert.AlertType.INFORMATION);
                }
            }
        });

/*
        Button dateKeywordLogBtn = new Button("日期 + 关键字查询");
        dateKeywordLogBtn.setPrefSize(360, 10);
        gridPg3.add(dateKeywordLogBtn, 0, 5, 6, 1);
        dateKeywordLogBtn.setOnAction(actionEvent -> {
            if (!"".equals(keyWord.getText()) && keyWord.getText() != null) {
                String logSearchStr = Utils.setLogStr(sudoYRBPg3.isSelected(), checkInDatePickerPg3.getValue().toString(), keyWord.getText(), choiceBox.getValue().toString());
                System.out.println("logSearchStr = " + logSearchStr);
                if (!"".equals(logSearchStr))
                    Utils.copyStr(logSearchStr, "Log " + keywordBtn.getText());
            } else {
                Utils.showaAlert("提示", "请输入关键字", Alert.AlertType.INFORMATION);
            }
        });
*/
        Button linuxLookBtn = new Button("查看指令");
        linuxLookBtn.setPrefSize(360, 10);
        gridPg3.add(linuxLookBtn, 0, 3, 6, 1);
        linuxLookBtn.setOnAction(actionEvent -> {
            String logSearchStr = "";
            if (sudoYRBPg3.isSelected()) logSearchStr += TextPara.sudoStr;
            logSearchStr += TextPara.logStr;
            System.out.println("logSearchStr = " + logSearchStr);
            Utils.copyStr(logSearchStr, "Log " + linuxLookBtn.getText());
        });


        gridPg3.add(new Label("Log 日期 ： "), 0, 4);
        DatePicker checkInDatePickerPg3 = new DatePicker();
        checkInDatePickerPg3.setValue(LocalDate.now());
        checkInDatePickerPg3.setMaxHeight(100);
        checkInDatePickerPg3.setMaxWidth(270);
        gridPg3.add(checkInDatePickerPg3, 1, 4, 6, 1);

        gridPg3.add(new Label("Log操作 ： "), 0, 5);
        Button copyLogBtn = new Button("复制");
        copyLogBtn.setPrefSize(120, 10);
        copyLogBtn.setOnAction(actionEvent -> {
            String copyStr = TextPara.sudoStr + "cp -r /usr/local/tomcat702/logs/catalina.out." + checkInDatePickerPg3.getValue() + ".log /tmp/";
            Utils.copyStr(copyStr, "Log複製指令 ");
        });

        Button delLogBtn = new Button("删除");
        delLogBtn.setPrefSize(120, 10);
        delLogBtn.setOnAction(actionEvent -> {
            String copyStr = TextPara.sudoStr + "rm -f /tmp/catalina.out." + checkInDatePickerPg3.getValue() + ".log";
            Utils.copyStr(copyStr, "Log刪除指令 ");
        });
        gridPg3.add(copyLogBtn, 1, 5);
        gridPg3.add(delLogBtn, 2, 5);

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
            Utils.showaAlert("伺服器測試結果", Utils.hostText(paymentCB.getValue().toString(), propertiesManager.getProperty("masterAuth").toString(), "1", "pay"), Alert.AlertType.INFORMATION);
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
            Utils.showaAlert("伺服器測試結果", Utils.hostText(autoPayCB.getValue().toString(), propertiesManager.getProperty("masterAuth").toString(), "1", "autopay"), Alert.AlertType.INFORMATION);
        });

        gridPg4.add(new Label("域名测试 ： "), 0, 2);
        TextField hostTest = new TextField();
        hostTest.setMaxWidth(220);
        hostTest.setPromptText("測試主機連線狀態");
        gridPg4.add(hostTest, 1, 2, 2, 1);

        Button hostTestBtn = new Button("测试");
        gridPg4.add(hostTestBtn, 3, 2, 2, 1);
        hostTestBtn.setOnAction(event -> {
            System.out.println(" = " + Utils.getHostIp(hostTest.getText()));
            Utils.showaAlert("伺服器測試結果", Utils.hostText(Utils.getHostIp(hostTest.getText()), propertiesManager.getProperty("masterAuth").toString(), "1", "pay"), Alert.AlertType.INFORMATION);
        });

        gridPg4.add(new Label("取得IP ： "), 0, 3);

        TextField hostText = new TextField();
        hostText.setMaxWidth(220);
        hostText.setPromptText("輸入網址獲得IP");
        gridPg4.add(hostText, 1, 3, 2, 1);

        Button searchIpBtn = new Button("查詢");
        gridPg4.add(searchIpBtn, 3, 3, 2, 1);
        searchIpBtn.setOnAction(event -> {
            Utils.showaAlert("测试结果", "服务器IP为 ：" + Utils.getHostIp(hostText.getText()), Alert.AlertType.INFORMATION);
        });

        vBoxPg4.getChildren().addAll(gridPg4);
        hostTab.setContent(vBoxPg4);

        //第五頁簽

        gridPg5.add(new Label("商户密钥 ： "), 0, 0);
        TextField merPkey = new TextField();
        merPkey.setMaxWidth(270);
        merPkey.setText("Label");
        merPkey.clear();
        gridPg5.add(merPkey, 1, 0, 3, 1);

        gridPg5.add(new Label("解密IV ： "), 0, 1);
        TextField deIV = new TextField();
        deIV.setMaxWidth(270);
        deIV.setEditable(false);
        deIV.clear();
        gridPg5.add(deIV, 1, 1, 3, 1);

        gridPg5.add(new Label("AES密文 ： "), 0, 2);
        TextArea aesStr = new TextArea();
        aesStr.setEditable(true);
        aesStr.setWrapText(true);
        aesStr.setMaxHeight(100);
        aesStr.setMaxWidth(270);
        gridPg5.add(aesStr, 1, 2, 3, 1);

        gridPg5.add(new Label("MD5明文 ： "), 0, 3);
        TextArea deStr = new TextArea();
        deStr.setEditable(false);
        deStr.setWrapText(true);
        deStr.setMaxHeight(100);
        deStr.setMaxWidth(270);
        deStr.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() > 1)
                    Utils.copyStr(deStr.getText(), "加密明文");
            }
        });
        gridPg5.add(deStr, 1, 3, 3, 1);

        Button deStrBtn = new Button("明文解密");
        deStrBtn.setPrefSize(360, 10);
        gridPg5.add(deStrBtn, 0, 4, 4, 1);
        deStrBtn.setOnAction(actionEvent -> {
            String pkey = merPkey.getText();
            String iv = Utils.MD5Sign(pkey);
            deIV.setText(iv);
            deStr.setText(Utils.AESDecrypt(pkey, iv, aesStr.getText()));
        });

        vBoxPg5.getChildren().addAll(gridPg5);
        deCodeTab.setContent(vBoxPg5);
//視窗頁面配置
        tabPane.getTabs().addAll(linuxTab, dfStateTab, logTab, hostTab, deCodeTab);

        Label versionText = new Label(version);
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