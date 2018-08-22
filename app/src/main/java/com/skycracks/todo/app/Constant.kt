import android.widget.Toast

/**
 * Constant
 */
object Constant {
    /**
     * baseUrl
     */
    const val REQUEST_BASE_URL = "http://wanandroid.com/"
    /**
     * Share preferences name
     */
    const val SHARED_NAME = "_preferences"
    const val LOGIN_KEY = "login"
    const val USERNAME_KEY = "username"
    const val PASSWORD_KEY = "password"
    const val HAS_NETWORK_KEY = "has_network"
    const val REMEMBER_PASSWORD_KEY = "remember_password"
    const val TODO_TYPE_KEY = "type"
    const val TODO_DONE_KEY = "todo_done"

    /**
     * 添加todo
     */
    const val TODO_STATUS_ADD = "todo_add"

    /**
     * 更新todo
     */
    const val TODO_STATUS_UPDATE = "todo_update"

    /**
     * 展示todo
     */
    const val TODO_STATUS_SHOW = "todo_show"

    /**
     * 未完成
     */
    const val TODO_STATUS_NO = 0

    /**
     * 已完成
     */
    const val TODO_STATUS_DONE = 1

    /**
     * 只用这一个
     */
    const val TODO_TYPE_0 = 0
    /**
     * 工作
     */
    const val TODO_TYPE_1 = 1
    /**
     * 学习
     */
    const val TODO_TYPE_2 = 2
    /**
     * 生活
     */
    const val TODO_TYPE_3 = 3

    /**
     * result null
     */
    const val RESULT_SERVER_ERROR  = "服务器错误!"

    /**
     * result null
     */
    const val RESULT_NULL = "没有返回数据!"
    /**
     * Toast
     */
    @JvmField
    var showToast: Toast? = null
    /**
     * Debug
     */
    const val INTERCEPTOR_ENABLE = false
}