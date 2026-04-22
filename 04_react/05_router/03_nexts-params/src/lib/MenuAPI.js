import menus from "../data/menu-detail.json"

// 메뉴 데이터 전체 조회
export function getMenuList() {
    return menus;
}

export function getMenuByMenuCode(menuCode) {
    // filter: 배열에서 콜백함수가 true인 요소로만 배열 반환
    // URL 데이터는 문자열이기 때문에 비교를 위해 형변환 해준다.
    return menus.filter(menu => menu.menuCode === parseInt(menuCode))[0];
}

export function searchMenu(searchMenuName) {
    // match() : 포함 여부에 따라 인수 값이 포함되어 있으면 객체 반환
    return menus.filter(menu => menu.menuName.match(searchMenuName))
}