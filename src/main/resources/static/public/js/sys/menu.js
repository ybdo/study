$(function () {
    $('#table').bootstrapTable({
        url: 'menus/list',
        striped: true,
        showRefresh: true,
        search: true,
        pagination: true,
        toolbar: '#toolbar',
        columns: [{
            checkbox: true
        }, {
            field: 'menuId',
            title: '序号',
            formatter: function (value, row, index) {
                return index;
            }
        }, {
            field: 'menuId',
            title: 'ID'
        }, {
            field: 'name',
            title: '菜单名称'
        }, {
            field: 'parentName',
            title: '父菜单名称'
        }, {
            field: 'url',
            title: 'URL'
        }, {
            field: 'perms',
            title: '权限标识'
        }, {
            field: 'type',
            title: '状态',
            formatter: function (value) {
                if (value == 0) {
                    return '<button class="btn btn-xs btn-danger">目录</button>'
                } else if (value == 1) {
                    return '<button class="btn btn-xs btn-primary">菜单</button>'
                } else if (value == 2) {
                    return '<button class="btn btn-xs btn-success">按钮</button>'
                } else {
                    return "";
                }
            }
        }, {
            field: 'icon',
            title: '图标',
            formatter: function (value) {
                return value == null ? '' : '<i class="' + value + '"></i>';
            }
        }, {
            field: 'orderNum',
            title: '排序'
        }]
    });
});

var vm = new Vue({
    el: '#dtapp',
    data: {
        showList: true,
        title: null,
        menu:{}
    },
    methods: {
        add: function () {
            vm.showList = false;
            vm.menu={type: 1, parentName: null, parentId: 0, orderNumber: 0};
            vm.title = '添加';
            vm.getMenu();
        },
        update: function () {
            var row = getSelectedRow();
            // console.log(row);
            var menuId = row['menuId']
            if (menuId == null) {
                return;
            }
            ;
            $.get("menus/queryById/" + menuId, function (message) {
                vm.showList = false;
                vm.title = '修改';
                //给vm.menu赋值  并且添加了所有menu的属性
                vm.menu = message.menu;
                vm.menu.parentName = row['parentName'];
                vm.getMenu();
            });
        },
        del: function () {
            var rows = getSelectedRows();
            if (rows == null) {
                return;
            }
            ;
            layer.confirm('确定要删除你选中的数据??', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                var ids = new Array();
                $.each(rows, function (i, row) {
                    ids[i] = row['menuId']
                })
                $.ajax({
                    type: "POST",
                    url: 'menus/del',
                    data: JSON.stringify(ids),
                    success: function (message) {
                        if (message.code == 0) {
                            //用lazyer弹框提示
                            layer.msg(message.msg, {icon: 1});
                            refresh();
                        } else {
                            layer.msg(message.msg, {icon: 2});
                            refresh();
                        }
                    }
                })
            });
        },
        //提交按钮
        saveOrUpdate: function (event) {
            var url = vm.menu.menuId == null ? "menus/save" : "menus/update";
            $.ajax({
                type: 'POST',
                url: url,
                data: JSON.stringify(vm.menu),
                success: function (message) {
                    if (message.code == 0) {
                        layer.msg(message.msg, {icon: 1});
                        vm.reload();
                    } else {
                        layer.msg(message.msg, {icon: 2});
                    }
                }
            })
        },
        reload: function () {
            vm.showList = true;
            refresh();
        },
        //点击一级菜单执行的函数
        menuTree: function () {
            layer.open({
                type: 1,
                offset: '50px', //弹出位置
                skin: 'layui-layer-molv',//皮肤
                title: "选择菜单",//标题
                area: ['300px', '450px'],//大小
                shade: 0,//阴影
                shadeClose: false,//关闭阴影
                content: jQuery("#menuLayer"),//加载内容
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var treeObj = $.fn.zTree.getZTreeObj("menuTree");
                    var nodes = treeObj.getSelectedNodes();
                    vm.menu.parentId = nodes[0].menuId;
                    vm.menu.parentName = nodes[0].name;
                    layer.close(index);
                }
            });
        },
        //获取除了按钮的菜单列表
        getMenu: function () {
            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "menuId",
                        pIdKey: "parentId",
                        rootPId: -1
                    },
                    key: {
                        //取消掉默认的跳转事件
                        url: "nourl"
                    }
                }
            };
            //获取后台数据并且添加到tree中
            $.get("menus/getMenu", function (message) {
                //初始化tree数据
                var ztree = $.fn.zTree.init($("#menuTree"), setting, message.menuList);
                //编辑（update）时，打开tree，自动高亮选择的条目
                var node = ztree.getNodeByParam("menuId", vm.menu.parentId);
                //选中tree菜单中的对应节点
                ztree.selectNode(node);
            })
        }

    }
});