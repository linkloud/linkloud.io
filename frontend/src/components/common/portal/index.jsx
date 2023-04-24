import ReactDOM from "react-dom";

const Portal = ({ elementId, children }) => {
  console.log(elementId);
  const potal = document.getElementById(elementId);
  return potal && ReactDOM.createPortal(children, potal);
};

export default Portal;
