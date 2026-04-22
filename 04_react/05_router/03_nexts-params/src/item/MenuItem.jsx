import Link from "next/link";

export default function MenuItem({menu}) {
  return (
    <>
      <Link href={`/menu/${menu.menuCode}`}>
        <div>
          <h3>메뉴 이름: {menu.menu.menuName}</h3>
        </div>
      </Link>
    </>
  )
}