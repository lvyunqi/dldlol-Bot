import sys
import json
# 防止中文乱码
import io
sys.stderr = io.TextIOWrapper(sys.stderr.buffer, encoding='utf-8')
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

# 需要修改的函数
def main():
    """
    Args:
        Name: (str) 拓展插件名称
        Version: (str) 插件版本号
        Author: (str) 作者名
        GroupMessageEvent: (str) 收到群聊消息事件 (多文件名使用英文“,”(逗号)分割)
        PrivateMessageEvent: (str) 收到私聊消息事件 (多文件名使用英文“,”(逗号)分割)
        WholeMessageEvent: (str) 全部消息监听事件 (多文件名使用英文“,”(逗号)分割)
    """
    Name = "[Python]拓展插件SDK"
    Version = "1.0"
    Author = "夜空"
    GroupMessageEvent = "GroupMessageDemo"
    PrivateMessageEvent = "PrivateMessageDemo"
    WholeMessageEvent = "WholeMessageDemo"
    Pack(Name,Version,Author,GroupMessageEvent,PrivateMessageEvent,WholeMessageEvent)



def Pack(Name: str,Version: str,Author: str,GroupMessageEvent: str,PrivateMessageEvent: str,WholeMessageEvent: str) ->str:
    Data = {"Basics":{"Name":Name,"Version":Version,"Author":Author},
    "ExpandApi":{"GroupMessageEvent":GroupMessageEvent,"PrivateMessageEvent":PrivateMessageEvent,"WholeMessageEvent":WholeMessageEvent}}
    Data = json.dumps(Data,ensure_ascii=False)
    print(Data)

if __name__ == '__main__':
    main()