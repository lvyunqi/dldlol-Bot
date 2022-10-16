***
#### API名称：获取oauth2认证token（所有API将采用这种认证方式）

请求参数
<table border="1px" align="center" bordercolor="black" width="100%" height="300px">
    <tr align="center">
        <td>字段</td>
        <td>类型</td>
        <td>是否必选</td>
        <td>可为空</td>
        <td>默认值</td>
        <td>说明</td>
        <td>示例</td>
    </tr>
    <tr align="center">
        <td>grant_type</td>
        <td>string</td>
        <td>Y</td>
        <td>N</td>
        <td></td>
        <td>授权类型</td>
        <td>"password"</td>
    </tr>
    <tr align="center">
        <td>username</td>
        <td>string</td>
        <td>Y</td>
        <td>N</td>
        <td></td>
        <td>用户名</td>
        <td>"user1"</td>
    </tr>
    <tr align="center">
        <td>password</td>
        <td>string</td>
        <td>Y</td>
        <td>N</td>
        <td></td>
        <td>用户密码</td>
        <td>"1"</td>
    </tr>
    <tr align="center">
        <td>client_id</td>
        <td>string</td>
        <td>Y</td>
        <td>N</td>
        <td></td>
        <td>应用的id 跟管理员索要</td>
        <td>"KxlvQ2J8yRfYJXO9iSd2ugGUpamDZf0tS0B40HrI"</td>
    </tr>
    <tr align="center">
        <td>client_secret</td>
        <td>string</td>
        <td>Y</td>
        <td>N</td>
        <td></td>
        <td>应用密钥 跟管理员索要</td>
        <td>"UxkZHyXR31TpTvIRUGHYAiyJtxyAFKB4u
        koUvi3RnYZ8t5ceSelzN2n89hEvtYte4P0qGa0
        oRDiaBc5siJnERUJto8RbfR0VFl2ZtZ42U9oLT
        rfODdjjq8mmBXDZRGoe"</td>
    </tr>
</table>

返回结果

{
"text": {
"access_token": "2cbwATnNLIm2DkIqZtYX7I9OyJkqVB",
"token_type": "Bearer",
"expires_in": 604800,   # 7 days
"refresh_token": "moLhUYln2NldVb7n7XxSmkgVEzpYVi",
"scope": "read write groups"
},
"status_code": 200,
}

# **用户注册**
***

+ **说明**
>说明这是个用户注册接口

+ **请求url**
>https://www.xxxx.com/User/Register

+ **请求方式**
>Post

***
+ **请求参数**

| 请求参数 | 必选  | 参数类型 |   说明   |
| :------: | :---: | :------: | :------: |
| userName |  是   |  sring   |   昵称   |
| password |  是   |  sring   |   密码   |
|  mobile  |  否   |  sring   | 手机号码 |

+ **请求示例**
~~~ js
{
    "userName": "张三",
    "password": "12222",
    "mobile":"132222222",
}
~~~

+ **返回参数**

| 返回参数 | 参数类型 |             说明             |
| :------: | :------: | :--------------------------: |
| Ressult  |  sring   | 返回结果，Result节点简称res. |
| res.code |   int    |            状态码            |
| res.msg  |  sring   |           错误信息           |
| res.data |  sring   |             数据             |
+ **返回示例**
~~~ js
// 正确示例
{
	"result": {
		"code": 1,
		"msg": "返回成功",
		"data": "",
	}
}
// 错误示例
{
	"result": {
		"code": 0,
		"msg": "返回失败",
		"data": "",
	}
}
~~~