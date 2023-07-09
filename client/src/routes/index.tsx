import { createBrowserRouter } from "react-router-dom";

import { privateRoutes } from "./privateRoutes";
import { publicRoutes } from "./publicRoutes";

const router = createBrowserRouter([...privateRoutes, ...publicRoutes]);

export default router;
