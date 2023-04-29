import Header from "@/common/components/header";

const Layout = ({ children }) => {
  return (
    <>
      <Header />
      <main className="flex flex-col items-center justify-center w-full">
        {children}
      </main>
    </>
  );
};

export default Layout;
