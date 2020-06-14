package com.example.recyclerviewexample.internet;

/**
 *https://www.jianshu.com/p/7bb8a2d9a5f5#%E7%BD%91%E7%BB%9C%E5%9F%BA%E7%A1%80
 */
public class HttpDemo {

    /**
     * 五层模型
     * 应用层
     * 传输层
     * 网络层
     * 数据链路
     * 物理层
     *
     *
     * TCP：面向连接；全双工；提供可靠的服务，即无差错、不丢失、不重复且按序到达；
     * 拥塞控制、流量控制、超时重发、丢弃重复数据等等可靠性检测手段；面向字节流；
     * 每条TCP连接只能是点到点的；用于传输可靠性要求高的数据，协议上来说没有长度限制（但是使用中还是不建议过长数据传输）
     *
     * TCP特性：
     * 1.Tcp提供一种面向连接的，可靠的字节流服务
     * 2.仅有两方进行彼此哦通信，不能广播和多播
     * 3.TCP使用校验和，确认和重传机制来保证数据的顺序不变和非重复
     * 4.TCP给数据分解进行排序，并使用累积确认保证数据的顺序不变和非重复
     * 5.TCP是使用华东窗口机制来实现流量控制，通过动态改变窗口的大小进行拥塞控制
     *
     *
     * TCP握手：
     *
     *  client state              客户端                               服务器                                  server state
     *
     *  listen
     *                          1，SYN = 1,Seq = x
     *                        (SYN标志位1，指明客户端打算连接服务器的端口                                             listen
     *  SYSSENT                      以及初始化序列号x，保存在包头的序列号(Sequence Number)字段里。)
     *                        -------------------------->
     *
     *
     *                         2.SYN=1, ACK=1, seq=y, ACKnum=x+1
     *                         (服务器发回确认包(ACK)应答。即 SYN 标志位和 ACK 标志位均为1。                          SYN RCVD
     *                         服务器端选择自己 ISN 序列号，放到 Seq 域里，
     *                         同时将确认序号(Acknowledgement Number)设置为客户的 ISN 加1，即X+1。
     *                         发送完毕后，服务器端进入 SYN_RCVD 状态。)
     *                        <--------------------------
     *
     *
     *
     *                         3.ACK=1，ACKnum=y+1
     *                         (客户端再次发送确认包(ACK)，SYN 标志位为0，ACK 标志位为1，
     *                         并且把服务器发来 ACK 的序号字段+1，放在确定字段中发送给对方，并且在数据段放写ISN的+1)      ESTAB
     *                         发送完毕后，客户端进入 ESTABLISHED 状态，当服务器端接收到这个包时，
     *   ESTAB                      也进入 ESTABLISHED 状态，TCP 握手结束。
     *                         -------------------------->
     *
     *
     *
     *
     *
     * TCP挥手：
     *
     * client state              客户端                               服务器                                  server state
     * 	ESTAB																								ESTAB
     * 							1，FIN=1，seq=x
     * 							(假设客户端想要关闭连接，客户端发送一个 FIN 标志位置为1的包，
     * 	FIN_WAIT_1						表示自己已经没有数据可以发送了，但是仍然可以接受数据。)
     * 							发送完毕后，客户端进入 FIN_WAIT_1 状态。
     * 							-------------------------->	                                                 CLOSE_WAIT
     *
     *
     *
     *
     *
     * 							2.ACK=1，ACKnum=x+1
     * 							(服务器端确认客户端的 FIN 包，发送一个确认包，
     * 							表明自己接受到了客户端关闭连接的请求，但还没有准备好关闭连接。)
     * 	FIN_WAIT_2						发送完毕后，服务器端进入 CLOSE_WAIT 状态，客户端接收到这个确认包之后，
     * 							进入 FIN_WAIT_2 状态，等待服务器端关闭连接。
     * 							<--------------------------
     *
     *
     *
     * 							3.FIN=1，seq=y
     * 							(服务器端准备好关闭连接时，向客户端发送结束连接请求，FIN 置为1。                     LAST_ACK
     * 								发送完毕后，服务器端进入 LAST_ACK 状态，等待来自客户端的最后一个ACK。)
     * 	TIME_WAIT				<--------------------------
     *
     *
     *
     * 							4.ACK=1，ACKnum=y+1
     * 							(客户端接收到来自服务器端的关闭请求，发送一个确认包，并进入 TIME_WAIT状态，            CLOSED
     * 							等待可能出现的要求重传的 ACK 包。
     * 							服务器端接收到这个确认包之后，关闭连接，进入 CLOSED 状态。）
     * 							-------------------------->
     *
     *  CLOSED
     *
     *
     *
     * HTTP 和 HTTPS区别
     * HTTP（超文本传输协议）：运行在TCP之上；传输的内容是明文；端口是80
     * HTTPS（安全为目标的HTTP）：运行在SSL/TLS之上，SSL/TLS运行在TCP之上；传输的内容经过加密；端口是443
     *
     *
     *
     * HTTP 发展之路
     * HTTP1.0默认使用短连接，HTTP1.1开始默认使用长连接
     * HTTP1.1增加更多的请求头和响应头来改进和扩充HTTP1.0的功能，比如身份认证、状态管理和Cache缓存等
     * HTTP2.0的协议解析决定采用二进制格式，实现方便且健壮，不同于HTTP1.x的解析是基于文本，HTTP2.0 可以主动服务端推送：
     *
     *
     *
     * http 协议
     * 1.采用C/s模型
     * 2.使用TCP传输协议
     * 3.无连接
     * 4.无状态
     *
     *
     *
     * 1. 请求行 = 请求方法 + 空格 + URL + 看空格 + HTTP协议版本
     * 示例 ：GET /somedir/page.html HTTP/1.1
     *
     * 请求方法	说明
     * GET	请求获取Request-URI所标识的资源
     * POST	在Request-URI所标识的资源后附加新的数据
     * HEAD	请求获取由Request-URI所标识的资源的响应消息报头
     * PUT	请求服务器存储一个资源，并用Request-URI作为其标识
     * DELETE	请求服务器删除Request-URI所标识的资源
     * TRACE	请求服务器回送收到的请求信息，主要用于测试或诊断
     * CONNECT	保留将来使用
     * OPTIONS	请求查询服务器的性能，或者查询与资源相关的选项和需求
     *
     * 2. 报头行
     *
     *
     */

}
