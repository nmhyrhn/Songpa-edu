'use client';
import MenuItem from "@/item/MenuItem";
import { getMenuList } from "@/lib/MenuAPI";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import boxStyle from "./Menu.module.css"

export default function Menu() {

    const [menuList, setMenuList] = useState([]);
    const [searchValue, setSearchValue] = useState('');

    const router = useRouter(); // 라우터 객체 반환받기

    useEffect(() => {
        setMenuList(getMenuList())
    }, [])
    console.log(menuList);

    const onClickHandler = () => {
        // 검색 버튼을 누르면 해당 경로로 이동
        router.push(`/menu/search?menuName=${searchValue}`)
    }

    return (
        <>
            <h1>메뉴 페이지 입니다.</h1>
            <div>
                <input
                    type="search"
                    name="menuName"
                    value={searchValue}
                    onChange={(e) => setSearchValue(e.target.value)}
                />
                <button onClick={onClickHandler}>검색</button>
            </div>
            <div className={boxStyle.MenuBox}>
                {menuList.map(menu => <MenuItem key={menu.menuCode} menu={menu}/>)}
            </div>
        </>
    )
}