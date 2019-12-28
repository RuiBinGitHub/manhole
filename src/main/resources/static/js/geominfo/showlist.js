$(document).ready(function() {

	// 定义坐标转换
    var projection = new ol.proj.Projection({
        code: "EPSG:2326",
        extent: [793259.70, 799130.01, 870525.78, 848940.16],
        axisOrientation: "neu",
        units: "m",
    });
    proj4.defs("EPSG:2326", "+proj=tmerc+lat_0=22.31213333333334+lon_0=114.1785555555556+k=1+x_0=836694.05+y_0=819069.8+ellps=intl+towgs84=-162.619,-276.959,-161.764,0.067753,-2.24365,-1.15883,-1.09425+units=m +no_defs");
    ol.proj.addProjection(projection);
    ol.proj.addCoordinateTransforms("EPSG:4326", "EPSG:2326", function(coordinate) {
        return proj4("EPSG:4326", "EPSG:2326", coordinate);
    }, function(coordinate) {
        return proj4("EPSG:2326", "EPSG:4326", coordinate);
    });
    ol.proj.addCoordinateTransforms("EPSG:3857", "EPSG:2326", function(coordinate) {
        return proj4("EPSG:3857", "EPSG:2326", coordinate);
    }, function(coordinate) {
        return proj4("EPSG:2326", "EPSG:3857", coordinate);
    });
    /** ************************************************************************ */
    // 地图初始化
    var viewer = new Cesium.Viewer("main",{
        geocoder: true, //是否显示查找控件
        timeline: false, //是否显示时间控件
        animation: false, //是否显示动画控件
        baseLayerPicker: true, //是否显示图层选择控件
        sceneModePicker: true, //是否显示投影方式控件
        navigationHelpButton: true, //是否显示帮助信息控件
        infoBox: true,  //是否显示点击要素之后显示的信息
        imageryProvider: new Cesium.ArcGisMapServerImageryProvider({ // 页面展示底图
            url: "http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer",
        })
    });
    viewer._cesiumWidget._creditContainer.style.display = "none"; // 不显示版权声明
    // 设置弹出框内元素可以执行操作
    var iframe = document.getElementsByClassName("cesium-infoBox-iframe")[0];
    iframe.setAttribute("sandbox", "allow-same-origin allow-scripts allow-popups allow-forms"); 
    // 设置地图的默认显示中心点和俯瞰高度
    var center = ol.proj.transform([831800.000, 830400.000], "EPSG:2326", "EPSG:4326");
    viewer.camera.flyTo({  
        destination: Cesium.Cartesian3.fromDegrees(center[0], center[1], 150000)
    });
    /** ************************************************************************ */
    $("#list1 div span").each(function(i){
    	$(this).attr("id", $(this).text());
    });
    $("#showdata").html($("#list1").html());
    /** 设置输入框修改事件 */
    $("#textbox").on("input", function() {
    	var value = $(this).val();
    	if (value == "")
    		$("#showdata").html($("#list1").html());
    	else {
    		$("#showdata").html("");
    		$("#list1 div").each(function() {
    			var span = $(this).find("span");
    			if (span.attr("id").indexOf(value) != -1)
    				$("#showdata").append($(this).clone());
    		});
    	}
    	$("#showdata").show();
    	$("#btn1").val("-");
    	$("#showdata div span").each(function() {
    		var text = $(this).text();
    		var font = "<font color='#f00'>" + value + "</font>";
			var expr=new RegExp(value, "gm")
			$(this).html(text.replace(expr, font));
        });
    });
    // 显示和隐藏按钮
    $("#btn1").click(function() {
    	if ($(this).val() == "+") {
    		$("#showdata").html($("#list1").html());
    		$("#showdata").show();
    		$(this).val("-");
    	} else {
    		$("#showdata").hide();
    		$("#textbox").val("");
    		$(this).val("+");
    	}
    });
    /** 项目列表点击事件 */
    $("#showdata").on("click", "div", function() {
    	var x = $(this).find("span").data("x");
    	var y = $(this).find("span").data("y");
    	var center = ol.proj.transform([x, y], "EPSG:2326", "EPSG:4326");
    	viewer.camera.flyTo({  // 点击元素时页面跳转至指定坐标位置
	        destination: Cesium.Cartesian3.fromDegrees(center[0], center[1], 80)
	    });
    });
    /** ************************************************************************ */
    var scene = viewer.scene;
    var globe = scene.globe;
    globe.depthTestAgainstTerrain = true;
    // 获取管道数据绘画管道
    $("#list1 div").each(function(i) {
        var id = $(this).find("img").attr("id");
        var x = $(this).find("span").data("x");
    	var y = $(this).find("span").data("y");
        var center = ol.proj.transform([x, y], "EPSG:2326", "EPSG:4326");
        drawManhole(id, "", center[0], center[1], -0.5);
    });
    /** ************************************************************************ */
    //鼠标左键单击事件
    var titles = ["A","B","C","D","E","F","G","H","X","Y"];
    var handler = new Cesium.ScreenSpaceEventHandler(viewer.scene.canvas);
    handler.setInputAction(function(movement) {
        var pick = viewer.scene.pick(movement.position);
        if (Cesium.defined(pick) && pick.id.name == "沙井") {
            var manhole = Ajax("findinfo", {id: pick.id.id});
            if (manhole == null)
            	return false;
            var context = "";
            context += "<table id='tab1' style='width:100%;font-size:16px;font-family:Times New Roman;background-color:#E0E0E0;'>";
            context += "  <tr style='height:30px;background-color:#545454;'>";
            context += "    <td align='right'>Node ref.：</td>";
            context += "    <td align='center'>" + manhole.node + "</td>";
            context += "    <td align='right'>Area code：</td>";
            context += "    <td align='center'>" + manhole.areacode + "</td>";
            context += "  </tr>";
            context += "  <tr style='height:30px;background-color:#545454;'>";
            context += "    <td align='right'>Survey Name：</td>";
            context += "    <td align='center'>" + manhole.surveyname + "</td>";
            context += "    <td align='right'>Survey Date：</td>";
            context += "    <td align='center'>" + manhole.surveydate + "</td>";
            context += "  </tr>";
            context += "  <tr style='height:30px;background-color:#545454;'>";
            context += "    <td align='right'>Project no：</td>";
            context += "    <td align='center'>" + manhole.projectno + "</td>";
            context += "    <td align='right'>Manhole ID：</td>";
            context += "    <td align='center'>" + manhole.manholeid + "</td>";
            context += "  </tr>";
            context += "  <tr style='height:30px;background-color:#545454;'>";
            context += "    <td align='right'>WO no：</td>";
            context += "    <td align='center'>" + manhole.workorder + "</td>";
            context += "    <td align='right'>DSD ref：</td>";
            context += "    <td align='center'>" + manhole.drainage + "</td>";
            context += "  </tr>";
            context += "  <tr style='height:30px;background-color:#545454;'>";
            context += "    <td align='right'>Location：</td>";
            context += "    <td align='center' colspan=3>" + manhole.location + "</td>";
            context += "  </tr>";
            context += "</table>";
            context += "<table id='tab2' style='width:100%;margin-top:5px;font-size:16px;font-family:Times New Roman;background-color:#E0E0E0;'>";
            context += "  <tr style='height:30px;background-color:#545454;'>";
            context += "    <td colspan='10'>记录信息</td>";
            context += "  </tr>";
            context += "  <tr style='height:20px;background-color:#545454;'>";
            context += "    <td width='8%' align='center'>No</td>";
            context += "    <td width='17%' align='center'>Upstream Ref</td>";
            context += "    <td width='18%' align='center'>Pipe shape</td>";
            context += "    <td align='center' colspan=3>Pipe size (mm)</td>";
            context += "    <td width='16%' align='center'>Material</td>";
            context += "    <td width='16%' align='center'>Lining</td>";
            context += "  </tr>";
            for (var i = 0; i < manhole.pipes.length; i++) {
            	var pipe = manhole.pipes[i];
	        	context += "  <tr style='height:25px;background-color:#545454;'>";
	        	context += "    <td align='center'>" + titles[i] + "</td>";
	            context += "    <td align='center'>" + pipe.upstream + "</td>";
	            context += "    <td align='center'>" + pipe.shape + "</td>";
	            context += "    <td width='10%' align='center'>" + pipe.size1 + "</td>";
	            context += "    <td width='5%' align='center'>x</td>";
	            context += "    <td width='10%' align='center'>" + pipe.size2 + "</td>";
	            context += "    <td align='center'>" + pipe.material + "</td>";
	            context += "    <td align='center'>" + pipe.lining + "</td>";
	            context += "  </tr>";
			}
            context += "</table>";
            context += "<img id='img'/>";
            pick.id.description = context;
        }
    }, Cesium.ScreenSpaceEventType.LEFT_CLICK);
    
    // 鼠标页面滑动事件
    handler.setInputAction(function(movement) {
    	var ellipsoid = scene.globe.ellipsoid;
		var cartesian = scene.camera.pickEllipsoid(movement.endPosition, ellipsoid);
		var cartographic = ellipsoid.cartesianToCartographic(cartesian);
		lon = Cesium.Math.toDegrees(cartographic.longitude);
		lat = Cesium.Math.toDegrees(cartographic.latitude);
		var center1 = ol.proj.transform([lon, lat], "EPSG:4326", "EPSG:2326");
		var x = center1[0].toFixed(3), y = center1[1].toFixed(3);
		$("#shownate").text("坐标：" + x + "，" + y);
    }, Cesium.ScreenSpaceEventType.MOUSE_MOVE);
    
    
    /** 画井函数 */
    function drawManhole(id, description, lon, lat, heght) {
        var degree = Cesium.Cartesian3.fromDegrees(lon, lat, heght);
        var color = Cesium.Color.LIME;
        var model = viewer.entities.add({
            id: id,
            name: "沙井",
            position: degree,
            model: {
                uri: "/survey/model/manhole.glb",
                maximumSize: 1,
                maximumScale: 1,
                minimumPixelSize: 1,
                silhouetteColor: Cesium.Color.WHITE,
                debugShowBoundingVolume: false,
                debugWireframe: false,
                runAnimations: true,
                scale: 10
            }
        });
    }
    
    function Ajax(url, data) {
        var result = null;
        $.ajax({
            url: url,
            data: data,
            type: "post",
            async: false,
            datatype: "json",
            success: function(data) {
                result = data;
            }
        });
        return result;
    }
});