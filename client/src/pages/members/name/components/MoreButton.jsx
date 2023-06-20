import { KekbabHorizon } from "@/static/svg";

const MoreButton = ({ ...props }) => {
  return (
    <button
      type="button"
      className="ml-4 px-2.5 py-1 border-2 border-gray-300 rounded"
      {...props}
    >
      <KekbabHorizon className="h-4.5 w-4.5 fill-gray-600 stroke-gray-600" />
    </button>
  );
};

export default MoreButton;
