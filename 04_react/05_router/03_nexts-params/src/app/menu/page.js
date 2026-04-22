'use client';
import MenuItem from "@/item/MenuItem";
import { getMenuList } from "@/lib/MenuAPI";
import { useState, useEffect } from "react"

export default function Menu() {
  
  const [menuList, setMenuList] = useState([]);

  useEffect(() => {
    setMenuList(getMenuList());
  }, [])

  return (
    <>
      <h1>메뉴 페이지</h1>
      <div>
        {menuList.map(menu => <MenuItem key={menu.menuCode} menu={menu}/>)}
      </div>
    </>
  )
}