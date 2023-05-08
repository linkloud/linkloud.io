/** @type {import('tailwindcss').Config} */
export default {
  content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
  theme: {
    extend: {
      colors: {
        primary: {
          100: "#E3EDFE",
          200: "#C7DBFD",
          300: "#AAC4F9",
          400: "#92B0F3",
          500: "#6F92EC",
          600: "#516FCA",
          700: "#3751A9",
          800: "#233788",
          900: "#152471",
        },
      },
      maxWidth: {
        "2xs": "270px",
        sm: "368px",
        xl: "564px",
        "7xl": "1200px",
      },
    },
  },
  plugins: [],
};
