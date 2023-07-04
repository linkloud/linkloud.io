const plugin = require("tailwindcss/plugin");

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
          high: "#7291ED",
          medium: "#5276CE",
          low: "#FBFCFF",
        },
        success: {
          high: "#67AA0C",
          medium: "#81C611",
          low: "#A8DC45",
        },
        warn: {
          high: "#DB402E",
          medium: "#FF623F",
          low: "#FF946F",
        },
      },
      maxWidth: {
        "2xs": "270px",
        sm: "368px",
        xl: "564px",
        "3xl": "808px",
        "7xl": "1200px",
      },
    },
  },
  plugins: [
    plugin(function ({ addUtilities }) {
      addUtilities({
        ".link-shadow-xs": {
          "box-shadow":
            "0px 3px 4px -2px rgba(18, 18, 23, 0.06), 0px 0px 0px 1px rgba(18, 18, 23, 0.03)",
        },
        ".link-shadow-sm": {
          "box-shadow":
            " 0px 5px 8px -2px rgba(18, 18, 23, 0.10), 0px 0px 0px 1px rgba(18, 18, 23, 0.03)",
        },
        ".link-shadow-md": {
          "box-shadow":
            "0px 6px 10px -2px rgba(18, 18, 23, 0.15), 0px 0px 0px 1px rgba(18, 18, 23, 0.03)",
        },
        ".link-shadow-lg": {
          "box-shadow":
            "0px 8px 15px -2px rgba(18, 18, 23, 0.20), 0px 0px 0px 1px rgba(18, 18, 23, 0.03)",
        },
        ".link-shadow-xl": {
          "box-shadow":
            "0px 10px 20px -2px rgba(18, 18, 23, 0.23), 0px 0px 0px 1px rgba(18, 18, 23, 0.03)",
        },
      });
    }),
  ],
};
