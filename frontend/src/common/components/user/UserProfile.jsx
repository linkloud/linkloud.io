import { userProfile } from "@/static/images";

const UserProfile = ({ name, profileImage, size = "md" }) => {
  const sizeTable = {
    sm: "x-6 h-6",
    md: "x-10 h-10",
    lg: "x-14 h-14",
  };

  const imgClassName = `${sizeTable[size]} rounded-full`;

  return (
    <a className={`bg-gray-200 rounded-full cursor-pointer`}>
      <img
        src={profileImage || userProfile}
        alt={name}
        className={imgClassName}
      />
      <span className="hidden">{name}</span>
    </a>
  );
};

export default UserProfile;
