from nonebot import on_command
from nonebot.typing import T_State
from nonebot.adapters import Bot, Event

dlmenu = on_command("斗罗系统")

@dlmenu.handle()
async def handle_dlmenu(bot: Bot, event: Event, state: T_State):
    menu_dl = await get_menulist()
    await dlmenu.send(message=menu_dl)

async def get_menulist():
    n = '\n'
    first = '╭═★斗罗大陆★═╮'
    second = '角色菜单●战斗菜单'
    third = '╰═★══════★═╯'
    menulist = first + n + second + n + third
    return menulist