'use client';
import MenuItem from "@/item/MenuItem";
import { searchMenu } from "@/lib/MenuAPI";
import { useSearchParams } from "next/navigation"
import { useEffect, useState } from "react";

export default function MenuSearchResult() {

    const searchParam = useSearchParams(); // 쿼리 스트링 객체 가져오기

    //  쿼리스트링 ?menuName='열무' 에서 '열무' 값 추출
    const menuName = searchParam.get('menuName')
    console.log(menuName);

    const [menuList, setMenuList] = useState([]);

    useEffect(() => {
        setMenuList(searchMenu(menuName))
    }, []);

    return (
        <>
            <h1>검색 결과!</h1>
            <div>
                {menuList.map(menu => <MenuItem key={menu.menuCode} menu={menu}/>)}
            </div>
        </>
    )
}