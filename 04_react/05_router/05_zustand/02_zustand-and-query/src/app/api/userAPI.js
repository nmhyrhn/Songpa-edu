export const fetchUsers = async () => {
  const response = await fetch('https://jsonplaceholder.typicode.com/users');
  if(!response.ok) throw new Error('데이터를 가져오지 못했습니다.');
  return response.json();
};