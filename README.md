![image](https://github.com/153437803/FloatMenu1_v1.0/blob/master/Screenrecorder-2017-11-14-03-50-11-309.mp4 )  

```
适用场景：

1.不需要判断Window权限，也许用户不同意权限，窗口会失效
2.悬浮菜单在APP使用的每一个界面都存（可以选择星的不存在，例如登录页面）
3.当APP退出时，悬浮菜单会消失
4.松手悬浮窗口会自动贴边
```
```
实现思路：

1.ViewDragHelper，官方提供的一个处理拖拽滑动事件的类库，不明白的可以自行度娘一下
2.在BaseActivity里面动态判断那些界面需要悬浮窗口，那些界面不需要悬浮窗口， 动态设置布局
3.在onResume()，onPause()，onBackPressed()根据业务需求，动态判断什么时候需要结束隐藏显示浮动窗口
```
