import Link from "next/link";
import itemStyle from "./MenuItem.module.css"

export default function MenuItem({menu}) {
    return(
        <>
            <Link href={`/menu/${menu.menuCode}`}>
                <div className={itemStyle.MenuItem}>
                    <h3>메뉴 이름: {menu.menuName}</h3>
                    <h4>메뉴 가격: {menu.menuPrice}</h4>
                    <h4>카테고리: {menu.categoryName}</h4>
                </div>
            </Link>
        </>
    ) 
}