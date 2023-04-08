import ReactDOM from "react-dom";

const Portal = ({ elementId, children }) => {
  const potal = document.getElementById(elementId);
  return potal && ReactDOM.createPortal(children, root);
};

export default Portal;
