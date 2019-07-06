$(function () {
    $('#table').bootstrapTable({
        url: 'users/list',
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
            field: 'userId',
            title: 'ID',
        }, {
            field: 'username',
            title: '用户名'
        }, {
            field: 'email',
            title: '邮箱'
        }, {
            field: 'mobile',
            title: '电话'
        }, {
            field: 'status',
            title: '状态'
        }, {
            field: 'createTime',
            title: '创建是时间'
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
        user: {}
    },
    methods: {


        //添加操作
        add: function () {
            //展示表单页false 隐藏列表页
            vm.showList = false;
            vm.user = {type: 1};
            //设置页面标题
            vm.title = '添加';
            vm.showSel();
        },
        //获取角色列表  填入下拉框
        showSel: function () {
            alert("我进来了");
            $.ajax({
                type: 'GET',
                url: 'roles/list',
                success: function (data) {
                    console.log(data);
                    var h = "";
                    $.each(data, function (index, value) {
                        h += "<option value='" + value.roleId + "'>" + value.roleName
                            + "</option>"
                    });
                    // 查询界面
                    $("#addid").append(h);
                }
            });
        },
        //点击修改权限
        update: function () {
            var row = getSelectedRow();
            if (row == null) {
                return;
            }
            ;
            vm.showList = false;
            vm.title = '修改';
            vm.user.userId = row['userId'];
            vm.user.type = 0;
            vm.user.email = row['email'];
            vm.user.mobile = row['mobile']
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
                    ids[i] = row['userId']
                });
                $.ajax({
                    type: "POST",
                    url: 'users/del',
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
            var url = vm.user.userId == null ? "users/save" : "users/update";
            $.ajax({
                type: 'POST',
                url: url,
                data: JSON.stringify(vm.user),
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