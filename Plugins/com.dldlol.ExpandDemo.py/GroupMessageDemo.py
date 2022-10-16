import sys
# 防止中文乱码
import io
sys.stderr = io.TextIOWrapper(sys.stderr.buffer, encoding='utf-8')
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

def main(msg,userId,groupId):
    if msg == "Python测试":
        print("Python拓展群聊测试成功！") # 将结果直接打印
    else:
        print("pass") # 打印 pass 为跳过(强制必写)


if __name__ == '__main__':
    msg = sys.argv[1]
    userId = sys.argv[2]
    groupId = sys.argv[3]
    main(msg,userId,groupId)
    