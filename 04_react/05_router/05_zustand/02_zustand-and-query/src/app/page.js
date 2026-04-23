import Link from "next/link";

export default function Home() {
  
  const {isModalOpen, openModal, closeModal} = useUIStore();
  const {user, login, logout} = useUserStore();
  const {items, addItem} = useCartStore();
  
  return (
    <>

    <section>
      <Link href="/async">비동기 구현</Link>
    </section>


      <section>
        <h2>User: {user ? user.name : "로그인 전"}</h2>
        <button onClick={() => login({name: '판다'})}>로그인</button>
        <button onClick={logout}>로그아웃</button>
      </section>

      <section>
        <h2>장바구니 상품: {items.length}개</h2>
        <button onClick={() => addItem({id: Date.now(), name: '상품'})}>상품 추가</button>
      </section>

      <section>
        <button onClick={openModal}>모달 열기</button>
        {isModalOpen && (
          <div style={{backgroundColor: 'yellow', padding: '20px', position: 'absolute'}}>
            <p>공지사항 모달입니다.</p>
            <button onClick={closeModal}>모달 닫기</button>
          </div>
        )}
      </section>

    </>
  );
}