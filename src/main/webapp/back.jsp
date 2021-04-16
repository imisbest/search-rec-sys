<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    request.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>系统主页</title>
    <!--引入bootstrap css-->
    <link rel="stylesheet" href="${path}/statics/bootstrap/css/bootstrap.min.css">
    <!--引入jqgrid的bootstrap css-->
    <link rel="stylesheet" href="${path}/statics/jqgrid/css/ui.jqgrid-bootstrap.css">
    <!--引入jquery核心js-->
    <script src="${path}/statics/js/jquery-3.4.1.min.js"></script>
    <!--引入jqgrid核心js-->
    <script src="${path}/statics/jqgrid/js/jquery.jqGrid.min.js"></script>
    <!--引入jqgrid国际化js-->
    <script src="${path}/statics/jqgrid/i18n/grid.locale-cn.js"></script>
    <!--引入bootstrap组件js-->
    <script src="${path}/statics/bootstrap/js/bootstrap.min.js"></script>
    <script>
        $(function () {

            /*//切换样式*/
            $(".list-group").on("click", ".list-group-item", function () {
                //去掉所有
                $(".list-group-item").removeClass().addClass("list-group-item");
                //点击激活
                $(this).addClass("active");
            });
            /*<!--添加用户模态框-->*/
            $('#exampleModal').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget);
                var recipient = button.data('whatever');
                var modal = $(this);
                modal.find('.modal-title').text('New message to ' + recipient);
                modal.find('.modal-body input').val(recipient)
            })
            /*部门表查询所有*/
            $('#dept_list').click(function () {
                $.post('${path}/d/qa', function (data) {
                    console.log(data);
                    $('#thead').empty();
                    $('#tbody').empty();
                    $('#thead').append($('<th>部门ID</th>')).append($('<th>部门名称</th>'));
                    for (var i = 0; i < data.length; i++) {
                        $('#tbody').append($('<tr><td>' + data[i].id + '</td><td>' + data[i].name + '</td></tr>'));
                    }
                }, 'json');
            });

            $('#inputclear').click(function () {
                $.post('${path}/do/inputclear');
            });

            $('#inputreload').click(function () {
                $.post('${path}/do/inputreload');
            });




            $.post('${path}/h/add', function (map) {
                console.log("redis值;;" + map);

                $('#menuld').empty();
                $.each(map, function (k, v) {
                    console.log(k);
                    console.log(v);
                    if (v/10 > 10) {
                        $('#menuld').append($('<button class="btn btn-primary btn-xs" type="button">' + k + '<span class="badge" style=" color: red">' + v/10 + '</span></button>'));
                    } else {
                        $('#menuld').append($('<button class="btn btn-primary btn-xs" type="button">' + k + '<span class="badge">' + v/10 + '</span></button>'));
                    }
                });
            }, 'json');
            /*   */
            ttl();
        });

        function ttl() {
            $.post("${path}/d/qac", function (date) {
                console.log(date);
                $('#tagid').empty();
                for (var i = 0; i < date.length; i++) {
                    console.log("data;;" + date[i]);
                    if(date[i].length>=3){
                        $('#tagid').append($('<div style="background-color: pink" class="alert alert-info alert-dismissible col-sm-1 aa " title="' + date[i] + '" onclick="ftagid(this)">\n<button class="close" data-dismiss="alert"><span>&times;</span></button>\n' + date[i] + '\n</div>&nbsp;'));

                    }else{
                        $('#tagid').append($('<div class="alert alert-info alert-dismissible col-sm-1 aa " title="' + date[i] + '" onclick="ftagid(this)">\n<button class="close" data-dismiss="alert"><span>&times;</span></button>\n' + date[i] + '\n</div>&nbsp;'));

                    }

                }
            }, "json");
        }

        function ftagid(t) {
            /*console.log("t/" + t)
            console.log("t2/" + $(t).prop("title"))*/
            $.post('${path}/d/remove', {removeword: $(t).prop("title")}, function () {
                console.log("ftagid1");
                ttl();
            });
        }

        function add() {
            console.log($('#added').val());
            $.post("${path}/d/add", 'tag=' + $('#added').val(), function () {
                console.log("add1");
                ttl();
            });
        }
    </script>
</head>
<body>
<!--导航栏-->
<nav class="navbar navbar-default">
    <div class="container-fluid">

        <div class="navbar-header">
            <a class="navbar-brand">唐诗-宋词后台管理系统 <small>V1.0</small></a>
        </div>


        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-left">
                <li><input type="button" value="清空ES所有文档" class="btn btn-danger" id="inputclear"></li>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <li><input type="button" value="基于基础数据重建ES索引库" class="btn btn-primary" id="inputreload"></li>
            </ul>
        </div>
    </div>
</nav>

<!--布局系统 中心内容-->
<div class="container-fluid">
    <div class="row">
        <!--菜单-->
        <div class="col-sm-12">
            <script>
                $(function () {
                    //初始化表格 并查询所有
                    $("#empList").jqGrid({
                        styleUI: "Bootstrap",
                        url: "${path}/p/findAll",
                        datatype: "json",
                        mtype: "post",
                        colNames: ["id", "诗词名", "作者", "类型", "内容", "来源", "作者简介", "网络类型"/*, "类别"*/],
                        colModel: [
                            {name: "id", search: false},
                            {name: "name", editable: true},
                            {name: "author", editable: true},
                            {name: "type", editable: true},
                            {name: "content", editable: true},
                            {name: "href", editable: true},
                            {name: "authordes", editable: true},
                            {name: "origin", editable: true},
                            /*{
                                name: "category.name", editable: true, edittype: "select", editoptions: {
                                    multiple: false,
                                    dataUrl: "\${path}/c/qa",//select
                                },*/
                                /*formatter: function (value, options, row) {
                                    if (row.category) return row.category.name;
                                }
                            },*/
                        ],
                        pager: "#pager",
                        rowNum: 10,
                        rowList: [10],
                        viewrecords: true,
                        caption: "唐诗宋词列表",
                        //cellEdit: true,//开启单元格编辑功能
                        editurl: "${path}/p/edit",
                        autowidth: true,
                        height: 370,
                    }).navGrid(
                        '#pager',//参数1: 分页工具栏id
                        {add: true},   //参数2:开启工具栏编辑按钮
                        {closeAfterEdit: true, reloadAfterSubmit: true},//编辑面板的配置
                        {closeAfterAdd: true, reloadAfterSubmit: true},//添加面板的配置
                        {},//删除的配置
                        {
                            sopt: ['eq', 'ne', 'cn']
                        },//搜索的配置
                    );
                });

                //添加一行
                function saveRow() {
                    $("#empList").jqGrid('editGridRow', "new", {
                        height: 500,
                        reloadAfterSubmit: true,
                        closeAfterAdd: true
                    });
                }

                //删除一行
                function delRow(id) {
                    if (window.confirm("您确定要删除吗?")) {
                        //发送ajax请求删除
                        $.post("${path}/e/edit", {id: id, oper: "del"}, function () {
                            //刷新表格
                            $("#empList").trigger('reloadGrid');//刷新表格
                        });
                    }

                }

                //修改一行
                function editRow(id) {
                    console.log(id);
                    $("#empList").jqGrid('editGridRow', id, {
                        height: 500,
                        reloadAfterSubmit: true,
                        closeAfterEdit: true,
                    });
                }
            </script>
            <%--员工列表--%>
            <table id="empList"></table>

            <%--分页工具栏--%>
            <div id="pager"></div>
        </div>
        <div class="col-sm-6">
            <div id="menuld">

            </div>
        </div>
        <div class="col-sm-6">
            <div>
                <form class="navbar-form navbar-left " style="clear: left">
                    <div class="form-group">
                        <input class="form-control" placeholder="Search" type="text" id="added">
                    </div>
                    <button class="btn btn-default" type="button" onclick="add()">添加</button>
                </form>
                <br>
            </div>

            <div id="tagid" style="clear: left;padding: 5px;margin: 5px">
            </div>
        </div>

    </div>
</div>

</body>
</html>