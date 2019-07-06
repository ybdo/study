$(function () {
    $('#table').bootstrapTable({
        url: 'roles/list',
        striped: true,
        showRefresh: true,
        search: true,
        pagination: true,
        sidePagination: 'client',
        toolbar: '#toolbar',
        columns: [{
            checkbox: true,
        }, {
            field: '',
            title: '序号',
            formatter: function (value, row, index) {
                return index;
            }
        }, {
            field: 'roleId',
            title: 'ID',
        }, {
            field: 'roleName',
            title: '角色名称'
        }, {
            field: 'remark',
            title: '备注'
        }, {
            field: 'userName',
            title: '创建人'
        }, {
            field: 'createTime',
            title: '创建时间'
        }]
    });

});
var vm = new Vue({
    el: '#dtapp',
    data: {
        //控制页面展示
        showList: true,
        //页面标题
        title: null,
        //from表单的操作对象
        role: {}
    },
    methods: {
        //添加操作
        add: function () {
            //展示表单页false 隐藏列表页
            vm.showList = false;
            vm.role = {roleId: null, menuIds: null}
            //设置页面标题
            vm.title = '添加';
        },
        //点击修改权限
        update: function () {
            var row = getSelectedRow();
            if (row == null) {
                return;
            }
            ;
            vm.role.roleId = row['roleId'];
            vm.getMenuIds();

        },
        //点击修改权限时-获取到所有的权限
        getMenuIds: function () {
            //弹出权限列表
            layer.open({
                title: '权限',
                type: 1,
                area: ['900px', '700px'], //宽高
                content: $("#menulist"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    //获取选中的行
                    var rows = getSelectedRows1();
                    if (rows == null) {
                        return;
                    }
                    ;
                    var ids = "";
                    //将选中的行中的id遍历出来组合成一个id字符串
                    $.each(rows, function (i, row) {
                        if (i == 0) {
                            ids = row['menuId'];
                        } else {
                            ids = ids + "," + row['menuId'];
                        }
                    })
                    //改变权限框中的值
                    vm.role.menuIds = ids;
                    // //刷新权限列表
                    // $("#table1").bootstrapTable('refresh');
                    vm.saveOrUpdate();
                    layer.close(index);
                    vm.reload()
                }
            });
            //加载权限列表
            $('#table1').bootstrapTable({
                url: 'menus/chooseList/'+vm.role.roleId,
                striped: true,
                showRefresh: true,
                search: true,
                pagination: true,
                sidePagination: 'client',
                columns: [{
                    filed: 'checked',
                    checkbox: true,
                    formatter: vm.stateFormatter
                }, {
                    field: '',
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
                }]
            });

        },
        //设置当前行的默认选中状态
        stateFormatter: function (value, row) {
            if (row.state == true) {
                return {
                    disabled: false,//设置是否可用
                    checked: true//设置选中
                };
            }
            return value;
        },
        //删除
        del: function () {
            var rows = getSelectedRows();
            //如果返回值为null值终止后面的行为
            if (rows == null) {
                return;
            }
            ;
            layer.confirm('确定要删除你选中的数据??', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                var ids = new Array();
                $.each(rows, function (i, row) {
                    ids[i] = row['roleId']
                })
                $.ajax({
                    type: "POST",
                    url: 'roles/del',
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
            //三目运算符来判断是进行修改还是添加操作
            var url = vm.role.roleId == null ? "roles/save" : "roles/update";
            $.ajax({
                type: 'POST',
                url: url,
                data: JSON.stringify(vm.role),
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
        //操作成功后执行的页面跳转及刷新
        reload: function () {
            vm.showList = true;
            // refresh()在common.js中定义了刷新的方法
            refresh();
        }
    }
});