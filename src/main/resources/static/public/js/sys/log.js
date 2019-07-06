$(function () {
    $('#table').bootstrapTable({
        url: 'logs/listPage',
        striped: true,
        showRefresh: true,
        search: true,
        pagination: true,
        sidePagination: 'server',
        toolbar: '#toolbar',
        columns: [{
            checkbox: true
        }, {
            field: '',
            title: '序号',
            formatter: function (value, row, index) {
                return index;
            }
        }, {
            field: 'id',
            title: 'ID'
        }, {
            field: 'username',
            title: '用户'
        }, {
            field: 'operation',
            title: '执行操作'
        }, {
            field: 'method',
            title: '执行方法'
        }, {
            field: 'params',
            title: '传递参数'
        }, {
            field: 'ip',
            title: '主机地址'
        }, {
            field: 'createDate',
            title: '执行时间'
        }]
    });
});

var vm = new Vue({
    el: '#dtapp',
    methods: {
        del: function () {
            //获取选中的行   若有的返回 没有就返回null  getSelectedRows()(common.js)中自定义的方法
            var rows = getSelectedRows();
            //如果为空,就不执行下面的方法
            if (rows == null) {
                return;
            };
            layer.confirm('确定要删除你选中的数据??', {
                btn: ['确定', '取消'] //按钮
            }, function () {
                var ids = new Array();
                //将每一行的ID对应的值拿出来
                $.each(rows, function (i, row) {
                    ids[i] = row['id']
                })
                $.ajax({
                    type: "POST",
                    url: 'logs/del',
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
        }
    }
});