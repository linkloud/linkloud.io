import { GithubIcon } from "@/static/svg";

const Footer = () => {
  return (
    <footer className="w-full py-10">
      <a
        href="https://github.com/linkloud"
        target="_blank"
        className="flex items-center justify-center cursor-pointer"
      >
        <GithubIcon className="h-6 w-6 fill-gary-400" />{" "}
        <span className="ml-1">Github</span>
      </a>
    </footer>
  );
};

export default Footer;
